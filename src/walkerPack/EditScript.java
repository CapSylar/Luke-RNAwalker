package walkerPack;

// editScript is a wrapper around EditScriptSequence

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class EditScript
{
    String SourceSequenceHash ;
    String DestinationSequenceHash ;

    EditScriptSequence EditSequence ;

    public EditScript ( String SourceSequenceHash , String DestinationSequenceHash , Node editScript )
    {
        this.SourceSequenceHash = SourceSequenceHash;
        this.DestinationSequenceHash = DestinationSequenceHash;
        this.EditSequence = EditScriptSequence.fromXML(editScript);
    }

    public EditScript ( String sourceSequenceHash , String DestinationSequenceHash , EditScriptSequence sequence )
    {
        this.SourceSequenceHash = sourceSequenceHash;
        this.DestinationSequenceHash = DestinationSequenceHash;
        this.EditSequence = sequence;
    }


    public static EditScript fromXMLFile (String path) throws InternalApplicationException
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);

            /*
             * factory.setIgnoringElementContentWhitespace(true) won't work unless the xml doc has a dtd linked, won't work with xsd i tried
             * it removes all the empty #text nodes while the parser is working through the file, very efficient :)
             *
             */

            Document document = factory.newDocumentBuilder().parse(new File(path));

            // get the 2 hashes from the file

            String SourceSequenceHash = document.getElementsByTagName("SourceString").item(0).getTextContent();
            String DestinationSequenceHash = document.getElementsByTagName("DestinationString").item(0).getTextContent();

            Node editScript = document.getElementsByTagName("EditScript").item(0);

            return new EditScript( SourceSequenceHash , DestinationSequenceHash , editScript );
        }
        catch (SAXException | ParserConfigurationException | IOException e)
        {
            throw new InternalApplicationException("Invalid Diff Path!") ;
        }
        catch ( java.lang.NullPointerException excp )
        {
            throw new InternalApplicationException("Invalid Diff Path!") ;
        }
    }

    public void toXMLFile ( String path )
    {
        // save the current EditScript Object to XML

        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder() ;
            Document diffDoc = builder.newDocument() ;

            diffDoc.setDocumentURI("hello");
            Element root = diffDoc.createElement("Diff");
            diffDoc.appendChild( root );

            Element meta = diffDoc.createElement("meta");
            root.appendChild(meta);

            // crate the two elements that will hold our hashes

            Element SourceString = diffDoc.createElement("SourceString");
            Element DestinationString = diffDoc.createElement("DestinationString");
            meta.appendChild(SourceString) ;
            meta.appendChild(DestinationString) ;

            // HASH the two strings
            SourceString.setTextContent( this.SourceSequenceHash );
            DestinationString.setTextContent( this.DestinationSequenceHash );

            // "EditScript" Element is returned by the method

            root.appendChild(this.EditSequence.toXML(diffDoc)) ;

            // save XML DOM to file

            DOMSource domSource = new DOMSource(diffDoc);
            StreamResult streamResult = new StreamResult(new File(path)) ;


            Transformer transformer = TransformerFactory.newInstance().newTransformer() ;
            transformer.setOutputProperty(OutputKeys.INDENT , "yes" );

            DocumentType doctype = diffDoc.getImplementation().createDocumentType("doctype" , "hello" , "DiffScriptDefinition.dtd");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());

            transformer.transform( domSource , streamResult );
            GraphicalInterface.logManager.logMessage("Success!" , 1000 );

        }
        catch ( Exception excp )
        {
            GraphicalInterface.logManager.logError("Invalid Diff Save Path" , 3000 );
        }
    }

    public EditScript getReverse()
    {
        // reverse source and destination hashes and reverse the edit script itself
        return new EditScript( this.DestinationSequenceHash , this.SourceSequenceHash , this.EditSequence.getReverse() );
    }

    public void apply( Sequence sequence ) throws InternalApplicationException
    {
        if ( sequence.EqualToHash(this.SourceSequenceHash) )
        {
            this.EditSequence.apply(sequence); // use normals sequence
        }
        else if ( sequence.EqualToHash(this.DestinationSequenceHash) )
        {
            this.EditSequence.getReverse().apply(sequence);
        }
        else // error , edit script incompatible with given sequence
        {
           throw new InternalApplicationException("Incompatible Diff Specified!");
        }
    }
}
