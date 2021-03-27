package walkerPack;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DiffApplicator
{
    String SourceSeq;
    String PatchedSeq;

    public DiffApplicator(String sourceSeq)
    {
        this.SourceSeq = sourceSeq;
    }

    public void applyDiff ( String DiffPath )
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document sourceDocument = builder.parse(new File(SourceSeq));

            factory = DocumentBuilderFactory.newInstance() ;
            factory.setIgnoringElementContentWhitespace(true);

            /*
             * factory.setIgnoringElementContentWhitespace(true) won't work unless the xml doc has a dtd linked, won't work with xsd i tried
             * it removes all the empty #text nodes while the parser is working through the file, very efficient :)
             *
             */

            builder = factory.newDocumentBuilder();
            Document ESDocument = builder.parse(new File(DiffPath));

            String sourceSeq = sourceDocument.getElementsByTagName("sequence").item(0).getTextContent().trim();

            NodeList editList = ESDocument.getElementsByTagName("EditScript").item(0).getChildNodes(); // should only be one item


            /******* DEBUG PRINTS
             System.out.println(" Child nodes before filtering: " + editList.getLength()) ;

             for ( int i = 0 ; i < editList.getLength() ; ++i )
             {
             System.out.println("entry " + i + " : " + editList.item(i).getNodeName() + " content: " + editList.item(i).getTextContent());
             }

             *****************************/

            // fetch operations from the editList and apply them
            int sourceI = 0;
            for ( int i = 0 ; i < editList.getLength() ; ++i )
            {
                Node operation = editList.item(i) ;
                String current = operation.getNodeName();

                switch (current)
                {
                    case "Insert":
                    {
                        char gotNucl = operation.getChildNodes().item(0).getTextContent().charAt(0);
                        int dropIndex = Integer.parseInt(operation.getChildNodes().item(1).getTextContent());
                        sourceSeq = sourceSeq.substring(0, (dropIndex + sourceI)) + gotNucl + sourceSeq.substring(dropIndex + sourceI);
                        sourceI++;
                    }
                    break;

                    case "Delete":
                        NodeList x = operation.getChildNodes() ;
                        int index = Integer.parseInt(operation.getChildNodes().item(0).getTextContent());
                        sourceSeq = sourceSeq.substring(0,(index + sourceI)) + sourceSeq.substring((index+1+sourceI));
                        sourceI--;
                        break;

                    case "Update":
                        char gotNucl = operation.getChildNodes().item(0).getTextContent().charAt(0);
                        int dropIndex = Integer.parseInt(operation.getChildNodes().item(1).getTextContent());
                        sourceSeq = sourceSeq.substring(0,(dropIndex+sourceI)) + gotNucl + sourceSeq.substring((dropIndex+1+sourceI));
                        break;

                    default:
                        System.err.println("HOLY SHIT, unsupported operation parsed"); // TODO: remove from code for final release
                }
            }

            this.PatchedSeq = sourceSeq ;
        }

        catch ( Exception exc )
        {
            exc.printStackTrace();
        }

    }


    void SavePatchedSequence( String pathName )
    {
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder() ;
            // save the patch sequence in XML format at the pathname

            Document PatchedSeq = builder.newDocument() ;
            Element root = PatchedSeq.createElement("RNADataBank");
            PatchedSeq.appendChild(root);

            Element RNA = PatchedSeq.createElement("RNA") ;
            Element accession = PatchedSeq.createElement("accession") ;
            accession.setTextContent("xxx");

            Element description = PatchedSeq.createElement("description");
            description.setTextContent("xxx");

            Element length = PatchedSeq.createElement("length");
            length.setTextContent("" + this.PatchedSeq.length());

            Element sequence = PatchedSeq.createElement("sequence");
            sequence.setTextContent(this.PatchedSeq);

            // attach all elements above to RNA

            root.appendChild(RNA) ;

            RNA.appendChild(accession);
            RNA.appendChild(description);
            RNA.appendChild(length);
            RNA.appendChild(sequence);

            // save XML DOM to file at filePath

            Transformer tranformer = TransformerFactory.newInstance().newTransformer() ;

            DOMSource domSource = new DOMSource(PatchedSeq);
            StreamResult streamResult = new StreamResult(new File(pathName)) ;

            tranformer.transform( domSource , streamResult );
        }
        catch ( Exception exc )
        {
            exc.printStackTrace();
        }

    }


}
