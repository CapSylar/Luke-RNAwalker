package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InsertOperation implements EditOperation
{
    private int insertIndex ;
    private char charToInsert ;
    private int reverseIndex ;

    public InsertOperation(int insertIndex, char charToInsert , int reverseIndex )
    {
        this.insertIndex = insertIndex;
        this.charToInsert = charToInsert;
        this.reverseIndex = reverseIndex;
    }

    @Override
    public int apply(StringBuilder string , int offset )
    {
        string.insert( this.insertIndex + offset , this.charToInsert ) ;
        return 1;
    }

    @Override
    public EditOperation getReverse ()
    {
        return new DeleteOperation( this.reverseIndex , this.charToInsert , this.insertIndex );
    }

    @Override
    public Element toXML(Document doc)
    {
        Element localRoot = doc.createElement("Insert") ;
        Element nucl = doc.createElement("nucl");
        Element dropIndex = doc.createElement("insertIndex") ;
        Element reverse_index = doc.createElement("reverseIndex") ;

        localRoot.appendChild(nucl);
        localRoot.appendChild(dropIndex);
        localRoot.appendChild(reverse_index);

        nucl.setTextContent(""+this.charToInsert);
        dropIndex.setTextContent(""+this.insertIndex);
        reverse_index.setTextContent(""+this.reverseIndex);

        return localRoot;
    }
}
