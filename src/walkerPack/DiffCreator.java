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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DiffCreator
{
    private String RNAseq1 , RNAseq2 ;
    HashMap<Character,String> NuclEquivs; ;
    ArrayList<EditOP> editScript;

    public DiffCreator( String file1 , String file2 ) throws Exception
    {
        // extract the two sequences from the files
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder() ;

        Document doc = builder.parse( new File(file1)) ;
        Document doc1 = builder.parse( new File (file2) ) ;

        // save them as strings after stripping them
        this.RNAseq1 = doc.getElementsByTagName("sequence").item(0).getTextContent().replaceAll("[\n ]" , "" ) ;
        this.RNAseq2 = doc1.getElementsByTagName("sequence").item(0).getTextContent().replaceAll("[\n ]" , "" ) ;

        this.InitHashMap(); // init the map used for the nucleotide equivalences
    }

    private void InitHashMap ()
    {
        this.NuclEquivs = new HashMap<>();

        this.NuclEquivs.put('R' , "GA" ) ;
        this.NuclEquivs.put('M' , "AC" ) ;
        this.NuclEquivs.put('S' , "GC" ) ;
        this.NuclEquivs.put('V' , "GAC" ) ;
        this.NuclEquivs.put('N' , "GUACRMSV" ) ;
        this.NuclEquivs.put('A' , "A" ) ;
        this.NuclEquivs.put('G' , "G" ) ;
        this.NuclEquivs.put('U' , "U" ) ;
        this.NuclEquivs.put('C' , "C" ) ;
    }

    void BuildDiff ( int costUpdate , int costDelete , int costInsert ) throws Exception
    {
        int dp[][][] = new int[RNAseq1.length()+1][RNAseq2.length()+1][2] ;

        // [2] : one for edit distance , 1: to note were we came from , 0 from delete, 1 from update
        // ,2 from insert ( 0-from up , 1-from diagonal , 2-from left )

        dp[0][0][0] = 0 ;

        for ( int i = 1 ; i <= RNAseq2.length() ; ++i )
        {
            dp[0][i][0] = i ;
            dp[0][i][1] = 2 ; // we came from the left cell
        }

        for ( int i = 1 ; i <= RNAseq1.length() ; ++i )
        {
            dp[i][0][0] = i ;
            dp[i][0][1] = 0 ; // we came from the up cell
        }

        for ( int i = 1 ; i <= RNAseq1.length() ; ++i )
            for ( int j = 1 ; j <= RNAseq2.length() ; ++j )
            {
                // we have to note were we came from , calc each thing separately

                int update = dp[i-1][j-1][0] + (CostUpdate( RNAseq1.charAt(i-1) , RNAseq2.charAt(j-1) ) ? costUpdate : 0)  ;
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

//        System.out.println("RNA seq 1: " + RNAseq1);
//        System.out.println("RNA seq 2: " + RNAseq2);
//
//        for ( int i = 0 ; i < dp.length ; ++i )
//        {
//            for (int j = 0; j < dp[0].length; ++j)
//                System.out.print(dp[i][j][0] + " ");
//            System.out.println();
//        }
//
//        System.out.println("string edit distance is " + dp[RNAseq1.length()][RNAseq2.length()][0] );


        // backtrack now or try to

//        System.out.println("BACKTRACK MATRIX");

//        for ( int i = 0 ; i < dp.length ; ++i )
//        {
//            for (int j = 0; j < dp[0].length; ++j)
//                System.out.print(dp[i][j][1] + " ");
//            System.out.println();
//        }

        // start at last cell and backtrack

        this.editScript =  new ArrayList<>() ;

        int i = RNAseq1.length();
        int j = RNAseq2.length();

        while ( i != 0 || j != 0 )
            switch (dp[i][j][1] )
            {
                case 0:
                    editScript.add( new EditOP( OP_TYPE.DELETE , i-1 , '0' ) ) ; // '0' not used
                    --i ;
                    break ;
                case 1:
                    if ( dp[i-1][j-1][0] != dp[i][j][0] ) // check if this update costs anything
                        editScript.add( new EditOP( OP_TYPE.UPDATE , i-1 , RNAseq2.charAt(j-1) )) ;
                    --i ; --j ;
                    break ;
                case 2:
                    editScript.add( new EditOP( OP_TYPE.INSERT , i , RNAseq2.charAt(j-1)));
                    --j ;
                    break ;
            }

        Collections.reverse(editScript) ;
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
        diffDoc.setDocumentURI("asshole");
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
        SourceString.setTextContent( this.getMD5(this.RNAseq1));
        DestinationString.setTextContent(this.getMD5(this.RNAseq2));

        Element scriptRoot = diffDoc.createElement("EditScript");
        root.appendChild(scriptRoot);

        // create elements under diff script for every entry in script

        for ( int i = 0 ; i < this.editScript.size() ; ++i )
        {
            scriptRoot.appendChild(this.editScript.get(i).toXMLElement(diffDoc));
        }

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


    private String getMD5 ( String input ) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD5") ;
        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger no = new BigInteger(1,messageDigest);
        String hashtext = no.toString(16) ; // to hex

        return hashtext;
    }

    enum OP_TYPE
    {
        INSERT,
        DELETE,
        UPDATE
    } ;

    class EditOP
    {
        int s_index = 0 ; // we only index into the source sequence
        char nucl; // nucleotide, not used in case of delete

        OP_TYPE type ;

        EditOP ( OP_TYPE type , int s_index , char nucl )
        {
            this.type = type ;
            this.s_index  = s_index ;
            this.nucl = nucl ;
        }

        public Element toXMLElement( Document doc )
        {
            Element editElement = doc.createElement("default") ; // should not be used

            switch ( type )
            {
                case UPDATE:
                    editElement = doc.createElement("Update") ;
                    Element nucl = doc.createElement("nucl");
                    Element source = doc.createElement("dropIndex");
                    editElement.appendChild(nucl);
                    editElement.appendChild(source);

                    nucl.setTextContent(""+this.nucl);
                    source.setTextContent("" + this.s_index);
                    break;
                case DELETE:
                    editElement = doc.createElement("Delete");
                    Element delete_index = doc.createElement("index");
                    editElement.appendChild(delete_index);

                    delete_index.setTextContent(""+this.s_index);
                    break;
                case INSERT:
                    editElement = doc.createElement("Insert");
                    Element getIndex = doc.createElement("nucl");
                    Element dropIndex = doc.createElement("dropIndex") ;
                    editElement.appendChild(getIndex);
                    editElement.appendChild(dropIndex);

                    getIndex.setTextContent(""+this.nucl);
                    dropIndex.setTextContent(""+this.s_index);
                    break;
            }

            return editElement;
        }
    }
}





