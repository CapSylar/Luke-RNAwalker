import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class Starter {

    static int COST_UPDATE = 1;
    static int COST_DELETE = 1;
    static int COST_INSERT = 1;


    public static void main(String args[]) throws Exception
    {
        // using DOM parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
        DocumentBuilder builder = factory.newDocumentBuilder() ;

        Document doc = builder.parse( new File("testseq.xml")) ;
        Document doc1 = builder.parse( new File ("testseq2.xml") ) ;

        String RNAseq1 = doc.getElementsByTagName("sequence").item(0).getTextContent().replaceAll("[\n ]" , "" ) ;
        String RNAseq2 = doc1.getElementsByTagName("sequence").item(0).getTextContent().replaceAll("[\n ]" , "" ) ;


        // assume cost del = cost ins = cost update if diff = 1

        int dp[][][] = new int[RNAseq1.length()+1][RNAseq2.length()+1][2] ;

        // [2] : one for edit distance , 1: to note were we came from , 0 from delete, 1 from update , 2 from insert ( 0-from up , 1-from diagonal , 2-from left )

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

                int update = dp[i-1][j-1][0] + CostUpdate( RNAseq1.charAt(i-1) , RNAseq2.charAt(j-1) ) ;
                int delete = dp[i-1][j][0] + COST_DELETE ;
                int insert = dp[i][j-1][0] + COST_INSERT ;

                // TODO: rewrite this section for the love of god

                /* by changing the order of the comparisons we could prioritize certain operations later, we could pick the ops
                * that are the easiest on the program in terms of speed */

                if ( update <= delete )
                {
                    if ( update <= insert )
                    {
                        // pick update
                        dp[i][j][0] = update ;
                        dp[i][j][1] = 1 ;
                    }
                    else
                    {
                        dp[i][j][0] = insert ;
                        dp[i][j][1] = 2 ;
                    }
                }
                else // update > delete
                {
                    if( delete <= insert )
                    {
                        dp[i][j][0] = delete ;
                        dp[i][j][1] = 0 ;
                    }
                    else
                    {
                        dp[i][j][0] = insert ;
                        dp[i][j][1] = 2 ;
                    }
                }

//                dp[i][j][0] = Math.min( dp[i-1][j-1][0] + CostUpdate( RNAseq1.charAt(i-1) , RNAseq2.charAt(j-1) )  ,
//                        Math.min( dp[i-1][j][0] + 1  , dp[i][j-1][0] + 1 ) ) ;
            }

        System.out.println("RNA seq 1: " + RNAseq1);
        System.out.println("RNA seq 2: " + RNAseq2);

        for ( int i = 0 ; i < dp.length ; ++i )
        {
            for (int j = 0; j < dp[0].length; ++j)
                System.out.print(dp[i][j][0] + " ");
            System.out.println();
        }

        System.out.println("string edit distance is " + dp[RNAseq1.length()][RNAseq2.length()][0] );


        // backtrack now or try to

        System.out.println("BACKTRACK MATRIX");

        for ( int i = 0 ; i < dp.length ; ++i )
        {
            for (int j = 0; j < dp[0].length; ++j)
                System.out.print(dp[i][j][1] + " ");
            System.out.println();
        }

        // start at last cell and backtrack

        ArrayList<EditOP> editScript =  new ArrayList<>() ;

        int i = RNAseq1.length();
        int j = RNAseq2.length();

        while ( i != 0 || j != 0 )
        {
//            System.out.print( " we are at node i:" + i + " j:" + j + " at weight: " + dp[i][j][0] + " " ) ;
            switch (dp[i][j][1] )
            {
                case 0:
//                    editScript.add(("Delete{Source[" + i +  "]}"));
//                      editScript.add("D" + i);
                      editScript.add( new EditOP( OP_TYPE.DELETE , i , 0 ) ) ;
                    --i ;
                    break ;
                case 1:
                    if ( dp[i-1][j-1][0] != dp[i][j][0] ) // check if this update costs anything
//                        editScript.add(("Update{Source[" + i +  "],Sink["+j+"]}" ));
//                        editScript.add("U" + i + "X" +j ); // X for separation
                          editScript.add( new EditOP( OP_TYPE.UPDATE , i , j )) ;
                    --i ; --j ;
                    break ;
                case 2:
//                    editScript.add(( "Insert{Sink[" + j + "] , " + i + "}" ));
//                      editScript.add( "I" + j + "X" + i );
                      editScript.add( new EditOP( OP_TYPE.INSERT , i , j ));
                    --j ;
                    break ;
            }
        }

        Collections.reverse(editScript);

        System.out.println("edit script") ;

//        for ( int k = 0 ; k < editScript.size() ; ++k )
//            System.out.println( editScript.get(k) ) ;


        CreateDiffScriptXML( editScript );

        // for scripts
        // string indices start at 1
        // insert at 0 means before all characters of the string
        // insert at 1 means after the first character
        // so essentially insert at x inserts after index x
    }

    public static int CostUpdate ( char old_v , char new_v )
    {
        HashMap<Character,String> equivalences = new HashMap<>() ; // TODO: built every time nonononono, fine for now
        equivalences.put('R' , new String("GA") ) ;
        equivalences.put('M' , new String("AC") ) ;
        equivalences.put('S' , new String("GC") ) ;
        equivalences.put('V' , new String("GAC") ) ;
        equivalences.put('N' , new String("GUACRMSV") ) ;
        equivalences.put('A' , new String("A") ) ;
        equivalences.put('G' , new String("G") ) ;
        equivalences.put('U' , new String("U") ) ;
        equivalences.put('C' , new String("C") ) ;


        if ( equivalences.get(old_v).indexOf(new_v) != -1 ) // found
        {
            return 0 ;
        }

        return COST_UPDATE ;
    }

    public static void CreateDiffScriptXML ( ArrayList<EditOP> script ) throws Exception
    {
        // parse the script

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
        DocumentBuilder builder = factory.newDocumentBuilder() ;

        Document diffDoc = builder.newDocument() ;
        Element root = diffDoc.createElement("Diff") ;
        diffDoc.appendChild( root );

        Element scriptRoot = diffDoc.createElement("EditScript");
        root.appendChild(scriptRoot);

        // create elements under diff script for every entry in script

        for ( int i = 0 ; i < script.size() ; ++i )
        {
            scriptRoot.appendChild(script.get(i).toXMLElement(diffDoc));
        }

        // save XML DOM to file

        TransformerFactory transformerFactory = TransformerFactory.newInstance() ;
        Transformer tranformer = transformerFactory.newTransformer() ;

        DOMSource domSource = new DOMSource(diffDoc);
        StreamResult streamResult = new StreamResult(new File("SEQdiff.xml")) ;

        tranformer.transform( domSource , streamResult );
    }
}

enum OP_TYPE
{
    INSERT,
    DELETE,
    UPDATE
} ;

class EditOP
{
    int s_index = 0 ;
    int d_index = 0 ;
    OP_TYPE type ;

    EditOP ( OP_TYPE type , int s_index , int d_index )
    {
        this.type = type ;
        this.s_index  = s_index ;
        this.d_index = d_index ;
    }

    public Element toXMLElement( Document doc )
    {
        Element editElement = doc.createElement("default") ; // should not be used

        switch ( type )
        {
            case UPDATE:
                editElement = doc.createElement("Update") ;
                Element destination = doc.createElement("getIndex");
                destination.setTextContent(""+this.d_index);
                Element source = doc.createElement("dropIndex");
                source.setTextContent("" + this.s_index);
                editElement.appendChild(destination);
                editElement.appendChild(source);
                break;
            case DELETE:
                editElement = doc.createElement("Delete");
                Element delete_index = doc.createElement("index");
                delete_index.setTextContent(""+this.s_index);
                editElement.appendChild(delete_index);
                break;
            case INSERT:
                editElement = doc.createElement("Insert");
                Element getIndex = doc.createElement("getIndex");
                Element dropIndex = doc.createElement("dropIndex") ;

                getIndex.setTextContent(""+this.d_index);
                dropIndex.setTextContent(""+this.s_index);

                editElement.appendChild(getIndex);
                editElement.appendChild(dropIndex);
                break;

        }


        return editElement;
    }
}


