package OperationsWrappers;

import GUICode.RangePaneController;
import GUICode.SearchEnginePaneState;
import SearchGroupOperations.RangeOperator;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import walkerPack.GraphicalInterface;

public class RangeQueryGraphicalConstructor implements GraphicalConstructor
{
    public static RangePaneController controller;

    private Stage current;
    private SearchEnginePaneState Callback;
    private int slotIndex;

    @Override
    public void Construct(SearchEnginePaneState callback, int slotIndex)
    {
        current = new Stage();
        current.initStyle(StageStyle.UNDECORATED);
        current.initModality(Modality.APPLICATION_MODAL);
        current.initOwner(GraphicalInterface.PrimaryStage);
        current.setScene(GraphicalInterface.RangeQueryPane);
        current.show();

        this.Callback = callback;
        this.slotIndex = slotIndex;

        controller.setupCallback(this);
    }

    public void OnButtonPressed( String value )
    {
        try
        {
            current.close(); // close window, even if user input is erroneous

            double k = Double.parseDouble(value);
            this.Callback.setOperationInSlot( new RangeOperator(k) , slotIndex ,
                    "Range(>="+ k + ")");

        }
        catch ( Exception e )
        {
            //TODO: handle exception properly
        }
    }
}
