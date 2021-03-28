package walkerPack;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GFXController
{
    static int textLimit = 80;

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
        String append = Utilities.BrowseForFile("Browse for Seq 1");

        if ( append != null )
        {
            CalculateDiffPaneSequence1Field.setText(append);
            DiffPaneState.setSeq1Path(append);
        }
    }

    @FXML
    void OnCalculateDiffPaneButton2Pressed()
    {
        String append = Utilities.BrowseForFile("Browse for Seq 2");

        if ( append != null )
        {
            CalculateDiffPaneSequence2Field.setText(append);
            DiffPaneState.setSeq2Path(append);
        }
    }

    @FXML
    void OnCalculateDiffTabSaveDiffButtonPressed()
    {
        String append = Utilities.SaveFileLocation("Save Location for Diff Script");

        if ( append != null )
        {
            if ( append.length() > 45 )
                CalculateDiffPaneTextField.setText( "..." + append.substring(append.length()-40));
            else
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
        ApplyPaneState.applyDiff() ;
    }

    @FXML
    void OnApplyDiffPaneBrowseSeqButtonPressed()
    {
        String append = Utilities.BrowseForFile("Browse for Seq");

        if ( append != null )
        {
            ApplyDiffPaneSeqField.setText(append);
            ApplyPaneState.setSeq(append);
        }
    }

    @FXML
    void OnApplyDiffPaneBrowseScriptButtonPressed()
    {
        String append = Utilities.BrowseForFile("Browse for Diff Script");

        if ( append != null )
        {
            ApplyDiffPaneDiffField.setText(append);
            ApplyPaneState.setDiff(append);
        }
    }

    @FXML
    void OnApplyDiffPaneSaveButtonPressed()
    {
        String append = Utilities.SaveFileLocation("Save Location for Patched Sequence");

        if ( append != null )
        {
            if ( append.length() > 45 )
                ApplyDiffPaneResultField.setText( "..." + append.substring(append.length()-40));
            else
                ApplyDiffPaneResultField.setText(append);
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
    public void initialize()
    {
        HookTextFieldListeners(); // hook up the text field listeners
        InitTextFieldsContents() ;
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
