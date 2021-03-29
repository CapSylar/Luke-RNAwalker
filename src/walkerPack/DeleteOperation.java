package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DeleteOperation implements EditOperation
{
    private int deleteAtIndex ;
    private char charToDelete ;
    private int reverseIndex;

    /*
    * not needed in normal circumstances since we can just use the deleteAtIndex to remove the char in the source string
    * but since we want to be able to reverse the operation ( delete -> insert ) and since we don't store the source or destination
    * strings with the edit script thus we have to keep track of the charToDelete
     */

    public DeleteOperation(int deleteAtIndex , char charToDelete , int reverseIndex )
    {
        this.deleteAtIndex = deleteAtIndex;
        this.charToDelete = charToDelete;
        this.reverseIndex = reverseIndex;
    }

    @Override
    public int apply(StringBuilder string , int offset )
    {
        string.delete( this.deleteAtIndex + offset , this.deleteAtIndex + offset + 1 ) ;
        return -1;
    }

    @Override
    public EditOperation getReverse ()
    {
        return new InsertOperation(this.reverseIndex , this.charToDelete , this.deleteAtIndex );
    }

    @Override
    public Element toXML(Document doc)
    {
        Element localRoot = doc.createElement("Delete");
        Element charToDelete = doc.createElement("nucl");
        Element delete_index = doc.createElement("deleteIndex");
        Element reverse_index = doc.createElement("reverseIndex");

        // ORDER MATTERS
        localRoot.appendChild(charToDelete);
        localRoot.appendChild(delete_index);
        localRoot.appendChild(reverse_index);

        delete_index.setTextContent(""+this.deleteAtIndex);
        charToDelete.setTextContent(""+this.charToDelete);
        reverse_index.setTextContent(""+this.reverseIndex);

        return localRoot;
    }
}
