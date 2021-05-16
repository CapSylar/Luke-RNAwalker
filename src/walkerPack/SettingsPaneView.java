package walkerPack;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.text.Text;

public class SettingsPaneView
{
    private Text DiffField;
    private Text SaveReverseField;

    private JFXTextField InsertCostField;
    private JFXTextField DeleteCostField;
    private JFXTextField UpdateCostField;

    public SettingsPaneView(JFXTextField settingsPaneInsertCostField, JFXTextField settingsPaneDeleteCostField,
                             JFXTextField settingsPaneUpdateCostField, Text settingsPaneDiffField,
                             Text settingsPaneSaveReverseField)
    {
        InsertCostField = settingsPaneInsertCostField;
        DeleteCostField = settingsPaneDeleteCostField;
        UpdateCostField = settingsPaneUpdateCostField;
        DiffField = settingsPaneDiffField;
        SaveReverseField = settingsPaneSaveReverseField;
    }

    public void setDiffField(String diffField)
    {
        DiffField.setText(Utilities.TruncateIfLonger(diffField , 40));
    }

    public void setSaveReverseField(String saveReverseField)
    {
        SaveReverseField.setText(Utilities.TruncateIfLonger(saveReverseField , 40));
    }

    public void setInsertCostField(String insertCostField)
    {
        InsertCostField.setText(insertCostField);
    }

    public void setDeleteCostField(String deleteCostField)
    {
        DeleteCostField.setText(deleteCostField);
    }

    public void setUpdateCostField(String updateCostField)
    {
        UpdateCostField.setText(updateCostField);
    }

    public void initView()
    {
        // do all GUI initializations specific to this pane here
        // Settings pane, gets text field contents from the SettingsManager
        InsertCostField.setText(""+GraphicalInterface.currentManager.getInsertCost());
        DeleteCostField.setText(""+GraphicalInterface.currentManager.getDeleteCost());
        UpdateCostField.setText(""+GraphicalInterface.currentManager.getUpdateCost());
    }
}
