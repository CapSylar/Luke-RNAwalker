package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DeleteOperation implements EditOperation
{
    private int deleteAtIndex ;

    public DeleteOperation(int deleteAtIndex)
    {
        this.deleteAtIndex = deleteAtIndex;
    }

    @Override
    public int apply(StringBuilder string , int offset )
    {
        string.delete( this.deleteAtIndex + offset , this.deleteAtIndex + offset + 1 ) ;
        return -1;
    }

    @Override
    public EditOperation getReverse()
    {
        return null;
    }

    @Override
    public Element toXML(Document doc)
    {
        Element localRoot = doc.createElement("Delete");
        Element delete_index = doc.createElement("index");
        localRoot.appendChild(delete_index);

        delete_index.setTextContent(""+this.deleteAtIndex);

        return localRoot;
    }
}
