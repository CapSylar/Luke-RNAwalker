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
            EditScript currentScript = EditScript.fromXML( ESDocument.getElementsByTagName("EditScript").item(0) ); // parse script from xml

            currentScript.apply(this.PatchedSequence) ;
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

            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(pathName)) ;

            tranformer.transform( domSource , streamResult );
        }
        catch ( Exception exc )
        {
            exc.printStackTrace();
        }

    }


}
