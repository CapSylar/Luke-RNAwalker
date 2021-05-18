package walkerPack;

import SearchGroupOperations.SED;
import javafx.beans.property.DoubleProperty;

public class DiffCreator
{
    private Sequence RNAsequence1 , RNAsequence2 ;
    EditScriptSequence currentScript = new EditScriptSequence();

    public DiffCreator( String file1 , String file2 ) throws InternalApplicationException
    {
        // save them as strings after stripping them
        this.RNAsequence1 = Sequence.fromXML(file1);
        this.RNAsequence2 = Sequence.fromXML(file2);
    }


    public void BuildDiff ( int costUpdate , int costDelete , int costInsert , DoubleProperty progress )
    {
        // TOFIX: progress is used anymore here, cleanup the whole interface again

        double distance = ( new SED()).getSimilarity( RNAsequence1 , RNAsequence1 , this.currentScript );
        GraphicalInterface.logManager.logMessage("Success! Similarity = " + 1/(1 + distance) , 7000 );
        System.out.println("Found distance is " + distance );
    }

    public void SaveDiffScriptXML ( String fileName )
    {
        EditScript toSave = new EditScript( this.RNAsequence1.getMD5() , this.RNAsequence2.getMD5() , this.currentScript );
        toSave.toXMLFile(fileName);
    }
}





