package GUICode;

import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.text.Text;
import walkerPack.Utilities;

public class ApplyPaneView
{
    private Text SeqField;
    private Text DiffField;
    private Text ResultField;
    private JFXProgressBar ProgressBar;

    public ApplyPaneView(Text applyDiffPaneSeqField, Text applyDiffPaneDiffField, Text applyDiffPaneResultField, JFXProgressBar applyDiffPaneProgressBar)
    {
        SeqField = applyDiffPaneSeqField;
        DiffField = applyDiffPaneDiffField;
        ResultField = applyDiffPaneResultField;
        ProgressBar = applyDiffPaneProgressBar;
    }

    public void setSeqField(String seqField)
    {
        SeqField.setText(Utilities.TruncateIfLonger(seqField,60));
    }

    public void setDiffField(String diffField)
    {
        DiffField.setText(Utilities.TruncateIfLonger(diffField,60));
    }

    public void setResultField(String resultField)
    {
        ResultField.setText(Utilities.TruncateIfLonger(resultField,30));
    }

    public void setProgressBar(double v)
    {
        ProgressBar.progressProperty().set(v);
    }
}
