package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UpdateOperation implements EditOperation
{
    private int updateIndex ;
    private char newChar ;

    public UpdateOperation(int updateIndex, char newChar)
    {
        this.updateIndex = updateIndex;
        this.newChar = newChar;
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
        return null;
    }

    @Override
    public Element toXML(Document doc)
    {
        Element localRoot = doc.createElement("Update") ;
        Element nucl = doc.createElement("nucl");
        Element source = doc.createElement("updateIndex");
        localRoot.appendChild(nucl);
        localRoot.appendChild(source);

        nucl.setTextContent(""+this.newChar);
        source.setTextContent("" + this.updateIndex);

        return localRoot;
    }
}
