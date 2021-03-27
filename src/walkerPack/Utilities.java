package walkerPack;

import javafx.stage.FileChooser;

import java.io.File;

public class Utilities
{
    static FileChooser Chooser = new FileChooser();

    public static String BrowseForFile( String windowTitle )
    {
        Chooser.setTitle(windowTitle);
        Chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file","*.xml")) ;

        File file = Chooser.showOpenDialog(null) ;

        return file != null ? file.getAbsolutePath() : null ;
    }

    public static String SaveFileLocation ( String windowTitle )
    {
        Chooser.setTitle(windowTitle);

        File file = Chooser.showSaveDialog(null) ;

        return file != null ? file.getAbsolutePath() : null ;
    }
}
