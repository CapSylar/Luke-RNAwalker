package walkerPack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphicalInterface extends Application
{
    public static GFXController Controller ;
    public static SettingsManager currentManager ;
    public static Pane rootPane;
    public static LoggingManager logManager;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader currentLoader = new FXMLLoader() ;

        Parent root = currentLoader.load(getClass().getResource("CalculateDiffTab.fxml"));
        rootPane = ( Pane ) root  ;

        Pane snackBarPanePositive = currentLoader.load(getClass().getResource("SnackBarPaneGood.fxml"));
        Pane snackBarPaneNegative = currentLoader.load(getClass().getResource("SnackBarPaneError.fxml"));

        // init logging manager
        logManager = new LoggingManager(  rootPane , snackBarPanePositive , snackBarPaneNegative );

        GraphicalInterface.Controller = currentLoader.getController(); // store reference to controller

        primaryStage.setTitle("RNA-Walker");
        primaryStage.getIcons().add(new Image("walkerPack/ICON.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent ->
        {
            //TODO: move this elsewhere, good enough for now
            currentManager.save(); // save manager before closing
        });


        // TODO: move this elsewhere

        EquivalenceManager.initManager();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
