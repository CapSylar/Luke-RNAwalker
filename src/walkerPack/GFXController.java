package walkerPack;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;

public class GFXController
{
    FileChooser Chooser = new FileChooser();

    @FXML
    private JFXButton CalculateDiffPaneCalculateDiffButton;

    @FXML
    private JFXButton CalculateDiffPaneBrowse1Button;

    @FXML
    private JFXButton CalculateDiffPaneBrowse2Button;

    @FXML
    private JFXTextField CalculateDiffPaneSequence1Field;

    @FXML
    private JFXTextField CalculateDiffPaneSequence2Field;

    @FXML
    private JFXProgressBar CalculateDiffPaneProgressBar;

    @FXML
    private JFXButton CalculateDiffTabSaveDiffButton;

    @FXML
    private JFXTextField CalculateDiffPaneSequence1Field1;

    @FXML
    private JFXTextField CalculateDiffPaneSequence2Field1;

    @FXML
    private JFXProgressBar ApplyDiffPaneProgressBar;


    @FXML
    private Text CalculateDiffPaneTextField;

    @FXML
    void OnCalculateDiffPaneButton1Pressed()
    {
        String append = Utilities.BrowseForFile("Browse for Seq 1");

        if ( append != null )
        {
            CalculateDiffPaneSequence1Field.appendText(append);
            DiffPaneState.setSeq1Path(append);
        }
    }

    @FXML
    void OnCalculateDiffPaneButton2Pressed()
    {
        String append = Utilities.BrowseForFile("Browse for Seq 2");

        if ( append != null )
        {
            CalculateDiffPaneSequence2Field.appendText(append);
            DiffPaneState.setSeq2Path(append);
        }
    }

    @FXML
    void OnCalculateDiffTabSaveDiffButtonPressed()
    {
        String append = Utilities.SaveFileLocation("Save Location for Diff Script");

        if ( append != null )
        {
            CalculateDiffPaneTextField.setText(append);
            DiffPaneState.setDiffPath(append);
        }
    }

    @FXML
    void OnCalculateDiffPaneCalculateDiffButtonPressed()
    {
        DiffPaneState.Calculate();
    }


    // ******************************** APPLY DIFF PANE START

    @FXML
    private JFXButton ApplyDiffPaneApplyButton;

    @FXML
    private JFXButton ApplyDiffPaneBrowseSeqButton;

    @FXML
    private JFXButton ApplyDiffPaneBrowseScriptButton;

    @FXML
    private JFXTextField ApplyDiffPaneSeqField;

    @FXML
    private JFXTextField ApplyDiffPaneDiffField;

    @FXML
    private Text ApplyDiffPaneResultField;


    @FXML
    void OnApplyDiffPaneApplyButtonPressed()
    {
        ApplyPaneState.applyDiff() ;
    }

    @FXML
    void OnApplyDiffPaneBrowseSeqButtonPressed()
    {
        String append = Utilities.BrowseForFile("Browse for Seq");

        if ( append != null )
        {
            ApplyDiffPaneSeqField.appendText(append);
            ApplyPaneState.setSeq(append);
        }
    }

    @FXML
    void OnApplyDiffPaneBrowseScriptButtonPressed()
    {
        String append = Utilities.BrowseForFile("Browse for Diff Script");

        if ( append != null )
        {
            ApplyDiffPaneDiffField.appendText(append);
            ApplyPaneState.setDiff(append);
        }
    }
}
