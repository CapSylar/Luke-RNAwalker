package GUICode;

import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.text.Text;
import walkerPack.Utilities;

public class DiffPaneView
{
    private Text Sequence1Field;
    private Text Sequence2Field;
    private Text SaveLocationTextField;
    private JFXProgressBar ProgressBar;

    public DiffPaneView(Text sequence1Field, Text sequence2Field, Text saveLocationTextField, JFXProgressBar calculateDiffPaneProgressBar)
    {
        Sequence1Field = sequence1Field;
        Sequence2Field = sequence2Field;
        SaveLocationTextField = saveLocationTextField;
        ProgressBar = calculateDiffPaneProgressBar;
    }

    public void setSequence1Field(String sequence1Field)
    {
        Sequence1Field.setText(Utilities.TruncateIfLonger(sequence1Field,60));
    }

    public void setSequence2Field(String sequence2Field)
    {
        Sequence2Field.setText(Utilities.TruncateIfLonger(sequence2Field,60));
    }

    public void setSaveLocationTextField(String saveLocationTextField)
    {
        SaveLocationTextField.setText(Utilities.TruncateIfLonger(saveLocationTextField,30));
    }

    public void setProgressBarProgress(double v)
    {
        this.ProgressBar.progressProperty().set(v);
    }
}
