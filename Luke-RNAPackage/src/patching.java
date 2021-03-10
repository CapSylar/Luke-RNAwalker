import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class patching {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document sourceDocument = builder.parse(new File( "sourceSeq.xml" ));
        Document destDocument = builder.parse(new File( "destSeq.xml" ));
        Document ESDocument = builder.parse(new File( "seqdiff.xml" ));

        /*Element root = sourceDocument.getDocumentElement();
        System.out.println(root.getNodeName());*/

        String sourceSeq = sourceDocument.getElementsByTagName("sequence").item(0).getTextContent().trim();
        System.out.println("Source Sequence = " + sourceSeq);
        String destSeq = destDocument.getElementsByTagName("sequence").item(0).getTextContent().trim();
        System.out.println("Destination Sequence = " + destSeq);
        int sourcel = sourceSeq.length();

        NodeList flowList = ESDocument.getElementsByTagName("EditScript");
        for (int i = 0; i < flowList.getLength(); i++) {
            NodeList childList = flowList.item(i).getChildNodes();
            for (int j = 0; j < childList.getLength(); j++) {
                Node childNode = childList.item(j);
                if ("Insert".equals(childNode.getNodeName())) {
                    int getIndex = Integer.parseInt(childNode.getChildNodes().item(1).getTextContent());
                    int dropIndex = Integer.parseInt(childNode.getChildNodes().item(3).getTextContent());
                    if(dropIndex >= sourcel){
                        sourceSeq = sourceSeq + destSeq.charAt(getIndex);
                    }
                    else {
                        sourceSeq = sourceSeq.substring(0, dropIndex) + destSeq.charAt(getIndex) + sourceSeq.substring(dropIndex);
                    }
                    //System.out.println(sourceSeq);
                }
                if("Update".equals(childNode.getNodeName())) {
                    int getIndex = Integer.parseInt(childNode.getChildNodes().item(1).getTextContent());
                    int dropIndex = Integer.parseInt(childNode.getChildNodes().item(3).getTextContent());
                    sourceSeq = sourceSeq.substring(0,dropIndex) + destSeq.charAt(getIndex) + sourceSeq.substring(dropIndex+1);

                }
                if("Delete".equals(childNode.getNodeName())) {

                    int index = Integer.parseInt(childNode.getChildNodes().item(1).getTextContent());
                    sourceSeq = sourceSeq.substring(0,index) + sourceSeq.substring(index+1);

                }

            }
        }
        System.out.println("Transformed source sequence = " + sourceSeq);
        System.out.println("Equal?  "  + sourceSeq.equals(destSeq));

    }
}
