package walkerPack;

public class ReverserPaneState
{
    private static String DiffPath ;
    private static String ReverseSavePath ;

    public static String getDiffPath()
    {
        return DiffPath;
    }

    public static void setDiffPath(String diffPath)
    {
        DiffPath = diffPath;
    }

    public static String getReverseSavePath()
    {
        return ReverseSavePath;
    }

    public static void setReverseSavePath(String reverseSavePath)
    {
        ReverseSavePath = reverseSavePath;
    }

    public static void reverse()
    {
        // TODO: relocate this code chunk elsewhere

        EditScript Reversed = EditScript.fromXMLFile(DiffPath);

        if ( Reversed != null )
        {
            Reversed.getReverse().toXMLFile(ReverseSavePath);// save to path
        }
    }
}
