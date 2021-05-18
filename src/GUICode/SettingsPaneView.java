package GUICode;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.text.Text;
import walkerPack.Utilities;

public class SettingsPaneView
{
    private Text DiffField;
    private Text SaveReverseField;

    private JFXTextField InsertCostField;
    private JFXTextField DeleteCostField;
    private JFXTextField UpdateCostField;

    private JFXCheckBox EnableIDFBox;
    private JFXCheckBox EnableTFBox;

    public SettingsPaneView(JFXTextField settingsPaneInsertCostField, JFXTextField settingsPaneDeleteCostField,
                             JFXTextField settingsPaneUpdateCostField, Text settingsPaneDiffField,
                             Text settingsPaneSaveReverseField , JFXCheckBox enableIDFBox , JFXCheckBox enableTFBox )
    {
        InsertCostField = settingsPaneInsertCostField;
        DeleteCostField = settingsPaneDeleteCostField;
        UpdateCostField = settingsPaneUpdateCostField;
        DiffField = settingsPaneDiffField;
        SaveReverseField = settingsPaneSaveReverseField;

        EnableIDFBox = enableIDFBox;
        EnableTFBox = enableTFBox;
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

    public void setIDFBoxState( boolean state )
    {
        this.EnableIDFBox.setSelected(state);
    }

    public void setTFBoxState( boolean state )
    {
        this.EnableTFBox.setSelected(state);
    }
}
