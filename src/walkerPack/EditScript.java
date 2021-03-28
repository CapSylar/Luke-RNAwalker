package walkerPack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class EditScript
{
    private ArrayList<EditOperation> operations;

    public EditScript()
    {
        this.operations = new ArrayList<>();
    }

    public void pushBack ( EditOperation toAdd )
    {
        this.operations.add(toAdd);
    }

    public void pushFront ( EditOperation toAdd )
    {
        this.operations.add(0 , toAdd );
    }

    public String apply ( String sequence )
    {
        StringBuilder seq = new StringBuilder(sequence) ;
        int offset = 0 ;

        for ( int i = 0 ; i < this.operations.size() ; ++i )
        {
            offset += this.operations.get(i).apply(seq , offset); // apply each operation
        }

        return seq.toString();
    }

    public void apply ( Sequence Sequence )
    {
        StringBuilder seq = new StringBuilder(Sequence.getSequence()) ;
        int offset = 0 ;

        for ( int i = 0 ; i < this.operations.size() ; ++i )
        {
            offset += this.operations.get(i).apply(seq , offset); // apply each operation
        }

        Sequence.setSequence( seq.toString() );
    }

    public Element toXML( Document doc )
    {
        // returns the element "EditScript"
        Element localRoot = doc.createElement("EditScript");

        for ( int i = 0 ; i < this.operations.size() ; ++i )
        {
            localRoot.appendChild(this.operations.get(i).toXML(doc)) ;
        }

        return localRoot;
    }

    public static EditScript fromXML( Node root )
    {
        // root refers to the Element "EditScript"
        EditScript toReturn = new EditScript();

        NodeList editList = root.getChildNodes(); // should only be one item

        for ( int i = 0 ; i < editList.getLength() ; ++i )
        {
            toReturn.pushBack( EditOperation.fromXML(editList.item(i)) );
        }

        return toReturn;
    }
}
