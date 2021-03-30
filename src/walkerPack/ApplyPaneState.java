package walkerPack;

import javafx.beans.property.DoubleProperty;

class ApplyPaneState
{
   static String Seq = "" ;
   static String Diff = "" ;
   static String ResultingSeq = "" ;

    public static String getSeq()
    {
        return Seq;
    }

    public static void setSeq(String seq)
    {
        Seq = seq;
    }

    public static String getDiff()
    {
        return Diff;
    }

    public static void setDiff(String diff)
    {
        Diff = diff;
    }

    public static String getResultingSeq()
    {
        return ResultingSeq;
    }

    public static void setResultingSeq(String resultingSeq)
    {
        ResultingSeq = resultingSeq;
    }

    public static void applyDiff (DoubleProperty Progress )
    {
        try
        {
            DiffApplicator applicator = new DiffApplicator(ApplyPaneState.Seq) ; // does logging and checking itself

            //TODO: find a better way to do this
            // we check the save path before executing the apply because the apply routine updates the progress bar
            // and since the save may later fail because the path is invalid , the progress bar moves in this case
            // we don't want that!

            if ( ResultingSeq.isBlank() ) // invalid path
                throw new InternalApplicationException("Invalid Save Path Specified!"); // throw before trying to compute

            applicator.applyDiff(Diff , Progress ); // apply diff on sequence
            applicator.SavePatchedSequence(ResultingSeq); // save it at location specified
        }
        catch ( InternalApplicationException excp )
        {
            GraphicalInterface.logManager.logError(excp.getMessage() , 3000 );
        }

    }
}
