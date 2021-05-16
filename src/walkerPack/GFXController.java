package walkerPack;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GFXController
{
    private DiffPaneState DiffPaneStateInstance;
    private DiffPaneView DiffPaneViewInstance;

    private ApplyPaneState ApplyPaneStateInstance;
    private ApplyPaneView ApplyPaneViewInstance;

    private SettingsPaneState SettingsPaneStateInstance;
    private SettingsPaneView SettingsPaneViewInstance;

    @FXML
    public void initialize()
    {
        // Hook Model and View instance of the Diff Pane
        this.DiffPaneViewInstance = new DiffPaneView( this.CalculateDiffPaneSequence1Field , this.CalculateDiffPaneSequence2Field ,
                CalculateDiffPaneTextField , CalculateDiffPaneProgressBar );
        this.DiffPaneStateInstance = new DiffPaneState( this.DiffPaneViewInstance );

        // Hook Model and View instance of the Apply Diff Pane
        this.ApplyPaneViewInstance = new ApplyPaneView( ApplyDiffPaneSeqField , ApplyDiffPaneDiffField ,
                ApplyDiffPaneResultField , ApplyDiffPaneProgressBar );
        this.ApplyPaneStateInstance = new ApplyPaneState( this.ApplyPaneViewInstance );

        // Hook Model and View instance of the Settings Pane

        this.SettingsPaneViewInstance = new SettingsPaneView( SettingsPaneInsertCostField , SettingsPaneDeleteCostField ,
                SettingsPaneUpdateCostField, SettingsPaneDiffField , SettingsPaneSaveReverseField  );
        this.SettingsPaneStateInstance = new SettingsPaneState( this.SettingsPaneViewInstance );

        HookTextFieldListeners(); // hook up the text field listeners
        InitTextFieldsContents() ;
    }


//    @FXML
//    private JFXButton CalculateDiffPaneCalculateDiffButton;
//
//    @FXML
//    private JFXButton CalculateDiffPaneBrowse1Button;
//
//    @FXML
//    private JFXButton CalculateDiffPaneBrowse2Button;

    @FXML
    private Text CalculateDiffPaneSequence1Field;

    @FXML
    private Text CalculateDiffPaneSequence2Field;

    @FXML
    private JFXProgressBar CalculateDiffPaneProgressBar;

//    @FXML
//    private JFXButton CalculateDiffTabSaveDiffButton;

//    @FXML
//    private JFXTextField CalculateDiffPaneSequence1Field1;
//
//    @FXML
//    private JFXTextField CalculateDiffPaneSequence2Field1;

    @FXML
    private Text CalculateDiffPaneTextField;

    @FXML
    void OnCalculateDiffPaneLoadSeq1Pressed()
    {
        // Call State of Diff Pane
        this.DiffPaneStateInstance.LoadFirstSeqButtonPressed();
    }

    @FXML
    void OnCalculateDiffPaneButton2Pressed()
    {
        // Call State of Diff Pane
        this.DiffPaneStateInstance.LoadSecondSeqButtonPressed();
    }

    @FXML
    void OnCalculateDiffTabSaveDiffButtonPressed()
    {
        // Call State of Diff Pane
        this.DiffPaneStateInstance.SaveDiffButtonPressed();
    }

    @FXML
    void OnCalculateDiffPaneCalculateDiffButtonPressed()
    {
        this.DiffPaneStateInstance.Calculate(CalculateDiffPaneProgressBar.progressProperty());
    }

    // ******************************** APPLY DIFF PANE START

//    @FXML
//    private JFXButton ApplyDiffPaneApplyButton;
//
//    @FXML
//    private JFXButton ApplyDiffPaneBrowseSeqButton;
//
//    @FXML
//    private JFXButton ApplyDiffPaneBrowseScriptButton;

    @FXML
    private Text ApplyDiffPaneSeqField;

    @FXML
    private Text ApplyDiffPaneDiffField;

    @FXML
    private Text ApplyDiffPaneResultField;

//    @FXML
//    private JFXButton ApplyDiffPaneSaveButton;

    @FXML
    private JFXProgressBar ApplyDiffPaneProgressBar;

    @FXML
    void OnApplyDiffPaneBrowseSeqButtonPressed()
    {
        // Call State of Apply Diff Pane
        this.ApplyPaneStateInstance.loadSeqButtonPressed();
    }

    @FXML
    void OnApplyDiffPaneBrowseScriptButtonPressed()
    {
        // Call State of Apply Diff Pane
        this.ApplyPaneStateInstance.loadDiffButtonPressed();
    }

    @FXML
    void OnApplyDiffPaneSaveButtonPressed()
    {
        // Call State of Apply Diff Pane
        this.ApplyPaneStateInstance.saveButtonPressed();
    }

    @FXML
    void OnApplyDiffPaneApplyButtonPressed()
    {
        this.ApplyPaneStateInstance.applyDiff( ApplyDiffPaneProgressBar.progressProperty() ) ;
    }

    //*******************************
    // settings pane

    @FXML
    private JFXTextField SettingsPaneInsertCostField;

    @FXML
    private JFXTextField SettingsPaneDeleteCostField;

    @FXML
    private JFXTextField SettingsPaneUpdateCostField;

//    @FXML
//    private JFXButton SettingsPaneLoadDIffButton;

    @FXML
    private Text SettingsPaneDiffField;

    @FXML
    private Text SettingsPaneSaveReverseField;

//    @FXML
//    private JFXButton SettingsPaneSaveReverseButton;
//
//    @FXML
//    private JFXButton SettingsPaneReverseDiffButton;

    @FXML
    void OnSettingsPaneLoadDIffButtonPressed()
    {
        this.SettingsPaneStateInstance.loadDiffButtonPressed();
    }

    @FXML
    void OnSettingsPaneSaveReverseButtonPressed()
    {
        this.SettingsPaneStateInstance.saveReverseButtonPressed();
    }

    @FXML
    void OnSettingsPaneReverseDiffButtonPressed()
    {
        this.SettingsPaneStateInstance.reverse();
    }


    public void HookTextFieldListeners()
    {
        SettingsPaneInsertCostField.textProperty().addListener((observableValue, oldS, newS) ->
        {
            if ( !newS.isBlank() && newS.matches("\\d*") )
                SettingsPaneStateInstance.setInsertCost(newS);
        });

        SettingsPaneDeleteCostField.textProperty().addListener((observableValue, oldS, newS) ->
        {
            if ( !newS.isBlank() && newS.matches("\\d*") )
                SettingsPaneStateInstance.setDeleteCost(newS);
        });

        SettingsPaneUpdateCostField.textProperty().addListener((observableValue, oldS, newS) ->
        {
            if ( !newS.isBlank() && newS.matches("\\d*") )
                SettingsPaneStateInstance.setUpdateCost(newS);
        });
    }

    private void InitTextFieldsContents()
    {
        this.SettingsPaneViewInstance.initView();
    }
}
