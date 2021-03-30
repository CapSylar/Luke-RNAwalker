package walkerPack;

import javafx.beans.property.DoubleProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class EditScriptSequence
{
    private ArrayList<EditOperation> Operations;

    public EditScriptSequence()
    {
        this.Operations = new ArrayList<>();
    }

    public void pushBack ( EditOperation toAdd )
    {
        this.Operations.add(toAdd);
    }

    public void pushFront ( EditOperation toAdd )
    {
        this.Operations.add(0 , toAdd );
    }

//    public String apply ( String sequence )
//    {
//        StringBuilder seq = new StringBuilder(sequence) ;
//        int offset = 0 ;
//
//        for (int i = 0; i < this.Operations.size() ; ++i )
//        {
//            offset += this.Operations.get(i).apply(seq , offset); // apply each operation
//        }
//
//        return seq.toString();
//    }

    public void apply (Sequence Sequence , DoubleProperty Progress )
    {
        StringBuilder seq = new StringBuilder(Sequence.getSequence()) ;
        int offset = 0 ;

        Progress.set(0);
        for (int i = 0; i < this.Operations.size() ; ++i )
        {
            offset += this.Operations.get(i).apply(seq , offset); // apply each operation
            Progress.set( Progress.get() + i/ (float)(this.Operations.size()) );
        }

        Sequence.setSequence( seq.toString() );
    }

    public EditScriptSequence getReverse()
    {
        EditScriptSequence Reverse = new EditScriptSequence();

        // create new edit script with each operation being reversed

        for (int i = 0; i < this.Operations.size() ; ++i )
        {
            Reverse.pushBack(this.Operations.get(i).getReverse());
        }

        return Reverse;
    }

    public Element toXML( Document doc )
    {
        // returns the element "EditScript"
        Element localRoot = doc.createElement("EditScript");

        for (int i = 0; i < this.Operations.size() ; ++i )
        {
            localRoot.appendChild(this.Operations.get(i).toXML(doc)) ;
        }

        return localRoot;
    }

    public static EditScriptSequence fromXML( Node root )
    {
        // root refers to the Element "EditScript"
        EditScriptSequence toReturn = new EditScriptSequence();

        NodeList editList = root.getChildNodes(); // should only be one item

        for ( int i = 0 ; i < editList.getLength() ; ++i )
        {
            toReturn.pushBack( EditOperation.fromXML(editList.item(i)) );
        }

        return toReturn;
    }


}
