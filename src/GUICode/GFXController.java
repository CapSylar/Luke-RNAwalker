package GUICode;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class GFXController
{
    private SimilarityPaneState SimilarityPaneStateInstance;
    private SimilarityPaneView SimilarityPaneViewInstance;

    private DiffPaneState DiffPaneStateInstance;
    private DiffPaneView DiffPaneViewInstance;

    private ApplyPaneState ApplyPaneStateInstance;
    private ApplyPaneView ApplyPaneViewInstance;

    private SettingsPaneState SettingsPaneStateInstance;
    private SettingsPaneView SettingsPaneViewInstance;

    private SearchEnginePaneState SearchEnginePaneStateInstance;
    private SearchEnginePaneView SearchEnginePaneViewInstance;

    @FXML
    public void initialize()
    {
        // Hook Model and View instance of the Similarity Pane
        this.SimilarityPaneViewInstance = new SimilarityPaneView( SimilarityPaneMenuButton ,
                SimilarityPaneSequenceOneField , SimilarityPaneSequenceTwoField ,
                SimilarityPaneSequenceOneProcessingField , SimilarityPaneSequenceTwoProcessingField,
                SimilarityPaneTotalTimeField , SimilarityPaneSimilarityText );
        this.SimilarityPaneStateInstance = new SimilarityPaneState( this.SimilarityPaneViewInstance );

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
                SettingsPaneUpdateCostField, SettingsPaneDiffField , SettingsPaneSaveReverseField , SettingsPaneEnableIDFBox , SettingsPaneEnableTFBox  );
        this.SettingsPaneStateInstance = new SettingsPaneState( this.SettingsPaneViewInstance );

        // Hook Model and View instance of the Search Engine Pane

        this.SearchEnginePaneViewInstance = new SearchEnginePaneView( SearchEnginePaneResultsField , SearchEnginePaneMenuButton1,
                SearchEnginePaneMenuButton2 , SearchEnginePaneMenuButton3 , SearchEnginePaneMenuButton4 ,
                SearchEnginePaneMenuButton5 , SearchEnginePaneQueryField ,
                SearchEnginePaneOpTimeText , SearchEnginePaneTotalTimeText );

        this.SearchEnginePaneStateInstance = new SearchEnginePaneState( this.SearchEnginePaneViewInstance );
        HookTextFieldListeners(); // hook up the text field listeners
    }

    // Similarity Pane

    @FXML
    private MenuButton SimilarityPaneMenuButton;

    @FXML
    private JFXTextField SimilarityPaneSequenceOneField;

    @FXML
    private JFXTextField SimilarityPaneSequenceTwoField;

    @FXML
    private Text SimilarityPaneSequenceOneProcessingField;

    @FXML
    private Text SimilarityPaneSequenceTwoProcessingField;

    @FXML
    private Text SimilarityPaneTotalTimeField;

    @FXML
    private Text SimilarityPaneSimilarityText;

    @FXML
    void onSimilarityPaneComparePressed()
    {
        this.SimilarityPaneStateInstance.compareButtonPressed();
    }

    // Calculate Diff Pane

    @FXML
    private Text CalculateDiffPaneSequence1Field;

    @FXML
    private Text CalculateDiffPaneSequence2Field;

    @FXML
    private JFXProgressBar CalculateDiffPaneProgressBar;

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

    @FXML
    private Text ApplyDiffPaneSeqField;

    @FXML
    private Text ApplyDiffPaneDiffField;

    @FXML
    private Text ApplyDiffPaneResultField;

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

    @FXML
    private Text SettingsPaneDiffField;

    @FXML
    private Text SettingsPaneSaveReverseField;

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

        SearchEnginePaneQueryField.textProperty().addListener((observableValue, oldS, newS) ->
        {
            if ( !newS.isBlank() )
                SearchEnginePaneStateInstance.setQueryField(newS);
        });

        SimilarityPaneSequenceOneField.textProperty().addListener((observableValue, oldS, newS) ->
        {
            if ( !newS.isBlank() )
                SimilarityPaneStateInstance.setSequenceOne(newS);
        });

        SimilarityPaneSequenceTwoField.textProperty().addListener((observableValue, oldS, newS) ->
        {
            if ( !newS.isBlank() )
                SimilarityPaneStateInstance.setSequenceTwo(newS);
        });
    }

    @FXML
    private JFXCheckBox SettingsPaneEnableIDFBox;

    @FXML
    private JFXCheckBox SettingsPaneEnableTFBox;

    @FXML
    void SettingsPaneEnableIDFPressed() {
        this.SettingsPaneStateInstance.enableIDFPressed( SettingsPaneEnableIDFBox.isSelected() );
    }

    @FXML
    void SettingsPaneEnableTFPressed(ActionEvent event) {
        this.SettingsPaneStateInstance.enableTFPressed(SettingsPaneEnableTFBox.isSelected());
    }

    // ******************** Search Engine Tab Pane
    @FXML
    private TextArea SearchEnginePaneResultsField;

    @FXML
    private SplitMenuButton SearchEnginePaneMenuButton1;

    @FXML
    private SplitMenuButton SearchEnginePaneMenuButton2;

    @FXML
    private SplitMenuButton SearchEnginePaneMenuButton3;

    @FXML
    private SplitMenuButton SearchEnginePaneMenuButton4;

    @FXML
    private SplitMenuButton SearchEnginePaneMenuButton5;

    @FXML
    private JFXTextField SearchEnginePaneQueryField;

    @FXML
    private Text SearchEnginePaneOpTimeText;

    @FXML
    private Text SearchEnginePaneTotalTimeText;

    @FXML
    void OnSearchEnginePaneSearchPressed()
    {
        this.SearchEnginePaneStateInstance.SearchPressed();
    }



}
