package walkerPack;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GraphicalInterface extends Application
{
    public static GFXController Controller ;
    public static SettingsManager currentManager ;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader currentLoader = new FXMLLoader() ;

        Parent root = currentLoader.load(getClass().getResource("CalculateDiffTab.fxml"));

        GraphicalInterface.Controller = currentLoader.getController(); // store reference to controller

        primaryStage.setTitle("RNA-Walker");
        primaryStage.getIcons().add(new Image("walkerPack/ICON.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent windowEvent)
            {
                //TODO: move this elsewhere, good enough for now
                currentManager.save(); // save manager before closing
            }
        });
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
