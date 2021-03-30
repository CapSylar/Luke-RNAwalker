package walkerPack;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DiffApplicator
{
    String SourceSeqPath;
    Sequence PatchedSequence;

    public DiffApplicator(String sourceSeq)
    {
        this.SourceSeqPath = sourceSeq;
    }

    public void applyDiff ( String DiffPath )
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document sourceDocument = factory.newDocumentBuilder().parse(new File(SourceSeqPath));

            this.PatchedSequence = Sequence.fromXML(sourceDocument);

            factory = DocumentBuilderFactory.newInstance() ;
            factory.setIgnoringElementContentWhitespace(true);

            /*
             * factory.setIgnoringElementContentWhitespace(true) won't work unless the xml doc has a dtd linked, won't work with xsd i tried
             * it removes all the empty #text nodes while the parser is working through the file, very efficient :)
             *
             */

            Document ESDocument = factory.newDocumentBuilder().parse(new File(DiffPath));

            EditScriptSequence currentScript = EditScriptSequence.fromXML( ESDocument.getElementsByTagName("EditScript").item(0) ); // parse script from xml

            String SourceSequenceHash = ESDocument.getElementsByTagName("SourceString").item(0).getTextContent();
            String DestinationSequenceHash = ESDocument.getElementsByTagName("DestinationString").item(0).getTextContent();

            // TODO: consider moving this logic to EditScript Object + letting EditScript manage the 2 Hashes

            if ( this.PatchedSequence.EqualToHash(SourceSequenceHash) ) // ok , just used non-reversed edit script
            {
                currentScript.apply(this.PatchedSequence) ;
            }
            else if ( this.PatchedSequence.EqualToHash(DestinationSequenceHash) ) // apply reversed edit script to provided sequence
            {
                currentScript = currentScript.getReverse();
                currentScript.apply(this.PatchedSequence);
            }
            else
            {
                throw new Exception("provided sequence did not match either hash, edit script is not for provided sequence") ;
            }
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

            Document doc = builder.newDocument() ;
            Element root = doc.createElement("RNADataBank");
            doc.appendChild(root);

            Element RNA = this.PatchedSequence.toXML( doc ); // get sequence in XML format
            root.appendChild(RNA);

            // save XML DOM to file at filePath

            Transformer tranformer = TransformerFactory.newInstance().newTransformer() ;
            tranformer.setOutputProperty(OutputKeys.INDENT , "yes");

            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(pathName)) ;

            tranformer.transform( domSource , streamResult );

            GraphicalInterface.logManager.logMessage("Success!" , 1000 );
        }
        catch ( ParserConfigurationException | TransformerException | NullPointerException e )
        {
            GraphicalInterface.logManager.logError("Invalid Save Path Specified!" , 3000 );
        }
    }


}
