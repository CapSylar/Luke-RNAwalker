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

        try
        {
            EditScript Reversed = EditScript.fromXMLFile(DiffPath);
            Reversed.getReverse().toXMLFile(ReverseSavePath);// save to path
        }
        catch ( InternalApplicationException excp )
        {
            // log error specified
            GraphicalInterface.logManager.logError(excp.getMessage() , 3000 );

        }
    }
}
