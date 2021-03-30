package walkerPack;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DiffApplicator
{
    String SourceSeqPath;
    Sequence PatchedSequence;

    public DiffApplicator(String sourceSeq)
    {
        this.SourceSeqPath = sourceSeq;
    }

    public void applyDiff ( String DiffPath ) throws InternalApplicationException
    {
        this.PatchedSequence = Sequence.fromXML(SourceSeqPath);
        EditScript currentEditScript = EditScript.fromXMLFile(DiffPath);
        currentEditScript.apply( this.PatchedSequence );
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
