package walkerPack;

public class DiffPaneState
{
    private static String Seq1Path ;
    private static String Seq2Path ;
    private static String DiffPath ;


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

    public static void Calculate()
    {
        try
        {
            DiffCreator newCreator = new DiffCreator( Seq1Path , Seq2Path );
            newCreator.BuildDiff(1,1,1); // default costs for now
            newCreator.SaveDiffScriptXML(DiffPath);
        }
        catch ( Exception exc )
        {
            exc.printStackTrace();
        }

    }
}