package walkerPack;

public class Starter
{
    public static void main(String args[])
    {
        // load settings manager
        SettingsManager manager = SettingsManager.load() ; // load settings into app

        GraphicalInterface.currentManager = manager ; // bind manager
        GraphicalInterface.main(args); // launch graphical interface
    }
}
