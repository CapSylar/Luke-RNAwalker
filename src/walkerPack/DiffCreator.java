package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;

public class DiffCreator
{
    private Sequence RNAsequence1 , RNAsequence2 ;
    HashMap<Character,String> NuclEquivs; ;
    EditScript currentScript = new EditScript();

    public DiffCreator( String file1 , String file2 ) throws Exception
    {
        // extract the two sequences from the files
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder() ;

        Document doc = builder.parse( new File(file1)) ;
        Document doc1 = builder.parse( new File (file2) ) ;

        // save them as strings after stripping them
        this.RNAsequence1 = Sequence.fromXML(doc);
        this.RNAsequence2 = Sequence.fromXML(doc1);

        this.InitHashMap(); // init the map used for the nucleotide equivalences
    }

    private void InitHashMap ()
    {
        this.NuclEquivs = new HashMap<>();

        this.NuclEquivs.put('R' , "RGA" ) ;
        this.NuclEquivs.put('M' , "MAC" ) ;
        this.NuclEquivs.put('S' , "SGC" ) ;
        this.NuclEquivs.put('V' , "VGAC" ) ;
        this.NuclEquivs.put('N' , "NGUACRMSV" ) ;
        this.NuclEquivs.put('A' , "A" ) ;
        this.NuclEquivs.put('G' , "G" ) ;
        this.NuclEquivs.put('U' , "U" ) ;
        this.NuclEquivs.put('C' , "C" ) ;
    }

    void BuildDiff ( int costUpdate , int costDelete , int costInsert ) throws Exception
    {
        String StringSequence1 = this.RNAsequence1.getSequence() ;
        String StringSequence2 = this.RNAsequence2.getSequence() ;
        
        int dp[][][] = new int[StringSequence1.length()+1][StringSequence2.length()+1][2] ;

        // [2] : one for edit distance , 1: to note were we came from , 0 from delete, 1 from update
        // ,2 from insert ( 0-from up , 1-from diagonal , 2-from left )

        dp[0][0][0] = 0 ;

        for ( int i = 1 ; i <= StringSequence2.length() ; ++i )
        {
            dp[0][i][0] = i ;
            dp[0][i][1] = 2 ; // we came from the left cell
        }

        for ( int i = 1 ; i <= StringSequence1.length() ; ++i )
        {
            dp[i][0][0] = i ;
            dp[i][0][1] = 0 ; // we came from the up cell
        }

        for ( int i = 1 ; i <= StringSequence1.length() ; ++i )
            for ( int j = 1 ; j <= StringSequence2.length() ; ++j )
            {
                // we have to note were we came from , calc each thing separately

                int update = dp[i-1][j-1][0] + (CostUpdate( StringSequence1.charAt(i-1) , StringSequence2.charAt(j-1) ) ? costUpdate : 0)  ;
                int delete = dp[i-1][j][0] + costDelete ;
                int insert = dp[i][j-1][0] + costInsert ;

                /* by changing the order of the comparisons we could prioritize certain operations later, we could pick the ops
                 * that are the easiest on the program in terms of speed */

                dp[i][j][0] = Math.min( update, Math.min(delete,insert));

                if ( dp[i][j][0] == update )
                    dp[i][j][1] = 1 ;
                else if ( dp[i][j][0] == delete )
                    dp[i][j][1] = 0 ;
                else
                    dp[i][j][1] = 2 ;
            }


        currentScript = new EditScript();

        int i = StringSequence1.length();
        int j = StringSequence2.length();

        while ( i != 0 || j != 0 )
            switch (dp[i][j][1] )
            {
                case 0: // delete operation
                    currentScript.pushFront( new DeleteOperation( i-1 , StringSequence1.charAt(i-1) , j ));
                    --i ;
                    break ;
                case 1: // update operation
                    if ( dp[i-1][j-1][0] != dp[i][j][0] ) // check if this update costs anything
                        currentScript.pushFront( new UpdateOperation(i-1 , StringSequence2.charAt(j-1) ,
                                StringSequence1.charAt(i-1) , j-1 ));
                    --i ; --j ;
                    break ;
                case 2: // insert operation
                    currentScript.pushFront( new InsertOperation( i , StringSequence2.charAt(j-1) , j-1 ));
                    --j ;
                    break ;
            }
    }

    private Boolean CostUpdate ( char old_v , char new_v )
    {
        return (this.NuclEquivs.get(old_v).indexOf(new_v) == -1 );
    }

    public void SaveDiffScriptXML ( String fileName ) throws Exception
    {
        // parse the script

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder() ;

        Document diffDoc = builder.newDocument() ;
        diffDoc.setDocumentURI("hello");
        Element root = diffDoc.createElement("Diff") ;
        diffDoc.appendChild( root );


        Element meta = diffDoc.createElement("meta");
        root.appendChild(meta);

        // crate the two elements that will hold our hashes

        Element SourceString = diffDoc.createElement("SourceString");
        Element DestinationString = diffDoc.createElement("DestinationString");
        meta.appendChild(SourceString) ;
        meta.appendChild(DestinationString) ;

        // HASH the two strings
        SourceString.setTextContent( this.RNAsequence1.getMD5() );
        DestinationString.setTextContent( this.RNAsequence2.getMD5() );

        // "EditScript" Element is returned by the method

        root.appendChild(this.currentScript.toXML(diffDoc)) ;

        // save XML DOM to file

        DOMSource domSource = new DOMSource(diffDoc);
        StreamResult streamResult = new StreamResult(new File(fileName)) ;


        Transformer transformer = TransformerFactory.newInstance().newTransformer() ;
        transformer.setOutputProperty(OutputKeys.INDENT , "yes" );

        DocumentType doctype = diffDoc.getImplementation().createDocumentType("doctype" , "hello" , "DiffScriptDefinition.dtd");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

        transformer.transform( domSource , streamResult );
    }



}





