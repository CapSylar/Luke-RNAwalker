package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UpdateOperation implements EditOperation
{
    private int updateIndex ;
    private char newChar ;
    private char oldChar ;
    private int reverseIndex;

    public UpdateOperation(int updateIndex, char newChar , char oldChar , int reverseIndex )
    {
        this.updateIndex = updateIndex;
        this.newChar = newChar;
        this.oldChar = oldChar;
        this.reverseIndex = reverseIndex;
    }

    @Override
    public int apply(StringBuilder string , int offset )
    {
        string.replace( updateIndex + offset , updateIndex + offset + 1 , "" + newChar ) ;
        return 0;
    }

    @Override
    public EditOperation getReverse()
    {
        return new UpdateOperation( this.reverseIndex , this.oldChar , this.newChar , this.updateIndex );
    }

    @Override
    public Element toXML(Document doc)
    {
        Element localRoot = doc.createElement("Update") ;
        Element NewNucl = doc.createElement("newNucl");
        Element OldNucl = doc.createElement("oldNucl");
        Element source = doc.createElement("updateIndex");
        Element reverse_index = doc.createElement("reverseIndex");

        // ORDER MATTERS
        localRoot.appendChild(NewNucl);
        localRoot.appendChild(OldNucl);
        localRoot.appendChild(source);
        localRoot.appendChild(reverse_index);

        NewNucl.setTextContent(""+this.newChar);
        OldNucl.setTextContent(""+this.oldChar);
        source.setTextContent("" + this.updateIndex);
        reverse_index.setTextContent("" + this.reverseIndex);

        return localRoot;
    }
}
