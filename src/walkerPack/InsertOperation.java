package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

public class InsertOperation implements EditOperation
{
    private int insertIndex ;
    private char charToInsert ;

    public InsertOperation(int insertIndex, char charToInsert)
    {
        this.insertIndex = insertIndex;
        this.charToInsert = charToInsert;
    }

    @Override
    public int apply(StringBuilder string , int offset )
    {
        string.insert( this.insertIndex + offset , this.charToInsert ) ;
        return 1;
    }

    @Override
    public EditOperation getReverse()
    {
        return null;
    }

    @Override
    public Element toXML(Document doc)
    {
        Element localRoot = doc.createElement("Insert") ;
        Element nucl = doc.createElement("nucl");
        Element dropIndex = doc.createElement("dropIndex") ;
        localRoot.appendChild(nucl);
        localRoot.appendChild(dropIndex);

        nucl.setTextContent(""+this.charToInsert);
        dropIndex.setTextContent(""+this.insertIndex);

        return localRoot;
    }
}
