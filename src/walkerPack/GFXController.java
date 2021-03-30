package walkerPack;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GFXController
{
    @FXML
    private JFXButton CalculateDiffPaneCalculateDiffButton;

    @FXML
    private JFXButton CalculateDiffPaneBrowse1Button;

    @FXML
    private JFXButton CalculateDiffPaneBrowse2Button;

    @FXML
    private Text CalculateDiffPaneSequence1Field;

    @FXML
    private Text CalculateDiffPaneSequence2Field;

    @FXML
    private JFXProgressBar CalculateDiffPaneProgressBar;

    @FXML
    private JFXButton CalculateDiffTabSaveDiffButton;

    @FXML
    private JFXTextField CalculateDiffPaneSequence1Field1;

    @FXML
    private JFXTextField CalculateDiffPaneSequence2Field1;

    @FXML
    private Text CalculateDiffPaneTextField;

    @FXML
    void OnCalculateDiffPaneButton1Pressed()
    {
        CalculateDiffPaneProgressBar.progressProperty().set(0);
        String append = Utilities.BrowseForFile("Browse for Seq 1");

        if ( append != null )
        {
            CalculateDiffPaneSequence1Field.setText(Utilities.TruncateIfLonger(append,60));
            DiffPaneState.setSeq1Path(append);
        }
    }

    @FXML
    void OnCalculateDiffPaneButton2Pressed()
    {
        CalculateDiffPaneProgressBar.progressProperty().set(0);
        String append = Utilities.BrowseForFile("Browse for Seq 2");

        if ( append != null )
        {
            CalculateDiffPaneSequence2Field.setText(Utilities.TruncateIfLonger(append,60));
            DiffPaneState.setSeq2Path(append);
        }
    }

    @FXML
    void OnCalculateDiffTabSaveDiffButtonPressed()
    {
        CalculateDiffPaneProgressBar.progressProperty().set(0);
        String append = Utilities.SaveFileLocation("Save Location for Diff Script");

        if ( append != null )
        {
            CalculateDiffPaneTextField.setText(Utilities.TruncateIfLonger( append , 30 ));
            DiffPaneState.setDiffPath(append);
        }
    }

    @FXML
    void OnCalculateDiffPaneCalculateDiffButtonPressed()
    {
        DiffPaneState.Calculate(CalculateDiffPaneProgressBar.progressProperty());
    }

    // ******************************** APPLY DIFF PANE START

    @FXML
    private JFXButton ApplyDiffPaneApplyButton;

    @FXML
    private JFXButton ApplyDiffPaneBrowseSeqButton;

    @FXML
    private JFXButton ApplyDiffPaneBrowseScriptButton;

    @FXML
    private Text ApplyDiffPaneSeqField;

    @FXML
    private Text ApplyDiffPaneDiffField;

    @FXML
    private Text ApplyDiffPaneResultField;

    @FXML
    private JFXButton ApplyDiffPaneSaveButton;

    @FXML
    private JFXProgressBar ApplyDiffPaneProgressBar;

    @FXML
    void OnApplyDiffPaneApplyButtonPressed()
    {
        ApplyPaneState.applyDiff( ApplyDiffPaneProgressBar.progressProperty() ) ;
    }

    @FXML
    void OnApplyDiffPaneBrowseSeqButtonPressed()
    {
        ApplyDiffPaneProgressBar.progressProperty().set(0);
        String append = Utilities.BrowseForFile("Browse for Seq");

        if ( append != null )
        {
            ApplyDiffPaneSeqField.setText(Utilities.TruncateIfLonger(append,60));
            ApplyPaneState.setSeq(append);
        }
    }

    @FXML
    void OnApplyDiffPaneBrowseScriptButtonPressed()
    {
        ApplyDiffPaneProgressBar.progressProperty().set(0);
        String append = Utilities.BrowseForFile("Browse for Diff Script");

        if ( append != null )
        {
            ApplyDiffPaneDiffField.setText(Utilities.TruncateIfLonger(append,60));
            ApplyPaneState.setDiff(append);
        }
    }

    @FXML
    void OnApplyDiffPaneSaveButtonPressed()
    {
        ApplyDiffPaneProgressBar.progressProperty().set(0);
        String append = Utilities.SaveFileLocation("Save Location for Patched Sequence");

        if ( append != null )
        {
            ApplyDiffPaneResultField.setText(Utilities.TruncateIfLonger( append , 30));
            ApplyPaneState.setResultingSeq(append);
        }
    }

    //*******************************
    // settings pane

    @FXML
    private JFXTextField SettingsPaneInsertCostField;

    @FXML
    private JFXTextField SettingsPaneDeleteCostField;

    @FXML
    private JFXTextField SettingsPaneUpdateCostField;

    @FXML
    private JFXButton SettingsPaneLoadDIffButton;

    @FXML
    private Text SettingsPaneDiffField;

    @FXML
    private Text SettingsPaneSaveReverseField;

    @FXML
    private JFXButton SettingsPaneSaveReverseButton;

    @FXML
    private JFXButton SettingsPaneReverseDiffButton;

    @FXML
    public void initialize()
    {
        HookTextFieldListeners(); // hook up the text field listeners
        InitTextFieldsContents() ;
    }

    @FXML
    void OnSettingsPaneLoadDIffButtonPressed()
    {
        String append = Utilities.BrowseForFile("Browse for Diff");

        if ( append != null )
        {
            SettingsPaneDiffField.setText(Utilities.TruncateIfLonger(append , 40));
            ReverserPaneState.setDiffPath(append);
        }
    }

    @FXML
    void OnSettingsPaneSaveReverseButtonPressed()
    {
        String append = Utilities.SaveFileLocation("Save Reverse Diff");

        if ( append != null )
        {
            SettingsPaneSaveReverseField.setText(Utilities.TruncateIfLonger(append , 40));
            ReverserPaneState.setReverseSavePath(append);
        }
    }

    @FXML
    void OnSettingsPaneReverseDiffButtonPressed()
    {
        ReverserPaneState.reverse();
    }

        public void HookTextFieldListeners()
    {
        SettingsPaneInsertCostField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldS , String newS )
            {
                if ( !newS.isBlank() && newS.matches("\\d*") )
                {
                    SettingsPaneInsertCostField.setText(newS);
                    GraphicalInterface.currentManager.setInsertCost(newS);
                }
            }
        });

        SettingsPaneDeleteCostField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldS , String newS )
            {
                if ( !newS.isBlank() && newS.matches("\\d*") )
                {
                    SettingsPaneDeleteCostField.setText(newS);
                    GraphicalInterface.currentManager.setDeleteCost(newS);
                }
            }
        });

        SettingsPaneUpdateCostField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldS , String newS )
            {
                if ( !newS.isBlank() && newS.matches("\\d*") )
                {
                    SettingsPaneUpdateCostField.setText(newS);
                    GraphicalInterface.currentManager.setUpdateCost(newS);
                }
            }
        });
    }

    private void InitTextFieldsContents()
    {
        // Settings pane, gets text field contents from the SettingsManager
        SettingsPaneInsertCostField.setText(""+GraphicalInterface.currentManager.getInsertCost());
        SettingsPaneDeleteCostField.setText(""+GraphicalInterface.currentManager.getDeleteCost());
        SettingsPaneUpdateCostField.setText(""+GraphicalInterface.currentManager.getUpdateCost());
    }
}
