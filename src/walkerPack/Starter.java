package walkerPack;

import java.util.ArrayList;

public class Starter
{
    public static void main(String args[])
    {
    // load settings manager

    EquivalenceManager.initManager(); // handles all comparison by building a hashMap just once

    SettingsManager manager = SettingsManager.load() ; // load settings into app

    GraphicalInterface.currentManager = manager ; // bind manager
    GraphicalInterface.main(args); // launch graphical interface

    }
}
