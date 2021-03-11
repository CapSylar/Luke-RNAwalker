import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class patching {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document sourceDocument = builder.parse(new File("sourceSeq.xml"));
        Document destDocument = builder.parse(new File("destSeq.xml"));

        factory = DocumentBuilderFactory.newInstance() ;
        factory.setIgnoringElementContentWhitespace(true);

        /*
        * factory.setIgnoringElementContentWhitespace(true) won't work unless the xml doc has a dtd linked, won't work with xsd i tried
        * it removes all the empty #text nodes while the parser is working through the file, very efficient :)
        *
        */

        builder = factory.newDocumentBuilder();
        Document ESDocument = builder.parse(new File("seqdiff.xml"));

        String sourceSeq = sourceDocument.getElementsByTagName("sequence").item(0).getTextContent().trim();
        int sourcel = sourceSeq.length() ;
        System.out.println("Source Sequence = " + sourceSeq);
        String destSeq = destDocument.getElementsByTagName("sequence").item(0).getTextContent().trim();
        System.out.println("Destination Sequence = " + destSeq);

        NodeList editList = ESDocument.getElementsByTagName("EditScript").item(0).getChildNodes(); // should only be one item


        /********* DEBUG PRINTS
        System.out.println(" Child nodes before filtering: " + editList.getLength()) ;

        for ( int i = 0 ; i < editList.getLength() ; ++i )
        {
            System.out.println("entry " + i + " : " + editList.item(i).getNodeName() + " content: " + editList.item(i).getTextContent());
        }

         *//******************************/

        // fetch operations from the editList and apply them

        for ( int i = 0 ; i < editList.getLength() ; ++i )
        {
            Node operation = editList.item(i) ;
            String current = operation.getNodeName();

            switch (current)
            {
                case "Insert":
                    {
                        int getIndex = Integer.parseInt(operation.getChildNodes().item(0).getTextContent());
                        int dropIndex = Integer.parseInt(operation.getChildNodes().item(1).getTextContent());
                        if (dropIndex >= sourcel)
                        {
                            sourceSeq = sourceSeq + destSeq.charAt(getIndex);
                        } else
                            {
                            sourceSeq = sourceSeq.substring(0, dropIndex) + destSeq.charAt(getIndex) + sourceSeq.substring(dropIndex);
                        }
                    }
                    break;

                case "Delete":
                    NodeList x = operation.getChildNodes() ;
                    int index = Integer.parseInt(operation.getChildNodes().item(0).getTextContent());
                    sourceSeq = sourceSeq.substring(0,index) + sourceSeq.substring(index+1);
                    break;

                case "Update":
                    int getIndex = Integer.parseInt(operation.getChildNodes().item(0).getTextContent());
                    int dropIndex = Integer.parseInt(operation.getChildNodes().item(1).getTextContent());
                    sourceSeq = sourceSeq.substring(0,dropIndex) + destSeq.charAt(getIndex) + sourceSeq.substring(dropIndex+1);
                    break;

                default:
                    System.err.println("HOLY SHIT, unsupported operation parsed"); // TODO: remove from code for final release
            }
        }

        System.out.println("Transformed source sequence = " + sourceSeq);
        System.out.println("Equal?  "  + sourceSeq.equals(destSeq));

    }
}
