package walkerPack;

import javafx.beans.property.DoubleProperty;

public class DiffPaneState
{
    private static String Seq1Path = "" ;
    private static String Seq2Path = "" ;
    private static String DiffPath = "" ;

    public static String getSeq1Path()
    {
        return Seq1Path;
    }

    public static void setSeq1Path(String seq1Path)
    {
        Seq1Path = seq1Path;
    }

    public static String getSeq2Path()
    {
        return Seq2Path;
    }

    public static void setSeq2Path(String seq2Path)
    {
        Seq2Path = seq2Path;
    }

    public static String getDiffPath()
    {
        return DiffPath;
    }

    public static void setDiffPath(String diffPath)
    {
        DiffPath = diffPath;
    }

    public static void Calculate(DoubleProperty progress)
    {
        // we write to progress to indicate our progress
        try
        {
            DiffCreator newCreator = new DiffCreator( Seq1Path , Seq2Path ); // does error logging itself

            //TODO: find a better way to do this
            // we check the save path before executing the diff calc because the apply routine updates the progress bar
            // and since the save may later fail because the path is invalid , the progress bar moves in this case
            // we don't want that!

            if ( DiffPath.isBlank() )
                throw new InternalApplicationException("Invalid Diff Save Path" );

            newCreator.BuildDiff(GraphicalInterface.currentManager.getUpdateCost(),GraphicalInterface.currentManager.getDeleteCost()
                    ,GraphicalInterface.currentManager.getInsertCost() , progress ); // pull costs from settings manager that has the update costs
            newCreator.SaveDiffScriptXML(DiffPath);
        }
        catch ( InternalApplicationException exp )
        {
            GraphicalInterface.logManager.logError(exp.getMessage() , 3000 );
        }
    }
}
