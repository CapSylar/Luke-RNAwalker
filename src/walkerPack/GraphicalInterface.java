package walkerPack;

import GUICode.GFXController;
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
    public static Stage PrimaryStage;
    public static Scene KNNPane;
    public static Scene RangeQueryPane;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        PrimaryStage = primaryStage;

        FXMLLoader currentLoader = new FXMLLoader() ;

        Parent root = currentLoader.load(getClass().getResource("../resources/CalculateDiffTab.fxml"));
        rootPane = ( Pane ) root  ;

        Pane snackBarPanePositive = currentLoader.load(getClass().getResource("../resources/SnackBarPaneGood.fxml"));
        Pane snackBarPaneNegative = currentLoader.load(getClass().getResource("../resources/SnackBarPaneError.fxml"));
        KNNPane = new Scene(currentLoader.load(getClass().getResource("../resources/KNNPane.fxml"))) ;
        RangeQueryPane = new Scene( currentLoader.load(getClass().getResource("../resources/RangeQueryPane.fxml")));
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
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
