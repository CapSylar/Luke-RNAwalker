package GUICode;
import OperationsWrappers.RangeQueryGraphicalConstructor;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class RangePaneController
{
    private RangeQueryGraphicalConstructor currentAttached;

    @FXML
    private JFXTextField RangePaneTextField;

    @FXML
    void RangePaneButtonPressed()
    {
        currentAttached.OnButtonPressed(RangePaneTextField.getText());
    }

    @FXML
    public void initialize()
    {
        RangeQueryGraphicalConstructor.controller = this;
    }

    public void setupCallback(RangeQueryGraphicalConstructor constructor)
    {
        currentAttached = constructor;
    }

}
