package walkerPack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    @FXML
    private Button GetStarted;
    @FXML
    private Button CalcDiff;
    @FXML
    private Button ApplyDiff;
    @FXML
    private Button Settings;
    @FXML
    private Button File1Btn;
    @FXML
    private Button File2Btn;
    @FXML
    private Button CalcAndSaveBtn;
    @FXML
    private TextArea FileArea1;
    @FXML
    private TextArea FileArea2;

    // create a FileChooser Object
    FileChooser fc = new FileChooser();




    @FXML
    void onButtonClicked() throws Exception{

         Parent root = FXMLLoader.load(getClass().getResource("Calculator.fxml"));
         Stage window = (Stage) GetStarted.getScene().getWindow();
         window.setScene(new Scene(root, 800, 600));

    }


    @FXML
    void calcButtonClicked() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("DiffCalc.fxml"));
        Stage window = (Stage) CalcDiff.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }

    @FXML
    void applyDiffClicked() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ApplyDiff.fxml"));
        Stage window = (Stage) ApplyDiff.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }

    @FXML
    void settingButtonClicked() throws Exception{
        System.out.println("clicked settings");
    }


    @FXML
    void File1BtnClicked() throws Exception{

        fc.setTitle("file chooser 1");

        // set the restrictions to only choose XML files
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file","*.xml"));


        // set the selected file or Null in case of nothing selected
        File file = fc.showOpenDialog(null);




        if(file != null) {
            FileArea1.appendText(file.getAbsolutePath());
        }else{
            FileArea1.appendText("no file has been chosen");
        }

    }

    @FXML
    void File2BtnClicked() throws Exception{
        fc.setTitle("file chooser 2");
        FileArea2.appendText("");

        // set the restrictions to only choose XML files
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file","*.xml"));


        // set the selected file or Null in case of nothing selected
        File file = fc.showOpenDialog(null);

        if(file != null) {
            FileArea2.appendText(file.getAbsolutePath());
        }else{
            FileArea2.appendText("no file has been chosen");
        }

    }

    

    @FXML
    void CalcAndSaveBtnClicked() throws Exception{
        System.out.println("clicked");
        // robin you implement the code here to calculate and save as a file.
    }

}

