package GUICode;
import OperationsWrappers.KNNGraphicalConstructor;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class KNNPaneController
{
    private KNNGraphicalConstructor currentAttached;

    @FXML
    private JFXTextField KNNPaneTextField;

    @FXML
    public void initialize()
    {
        KNNGraphicalConstructor.controller = this;
    }

    public void setupCallback(KNNGraphicalConstructor constructor)
    {
        currentAttached = constructor;
    }

    @FXML
    void KNNPaneButtonPressed()
    {
        currentAttached.OnButtonPressed(KNNPaneTextField.getText());
    }
}
