package OperationsWrappers;

import GUICode.CallablePaneState;
import GUICode.RangePaneController;
import SearchGroupOperations.RangeOperator;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import walkerPack.GraphicalInterface;

public class RangeQueryGraphicalConstructor implements GraphicalConstructor
{
    public static RangePaneController controller;

    private Stage current;
    private CallablePaneState Callback;
    private int slotIndex;

    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
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

            // make sure k is within range
            if ( k < 0 || k > 1 )
            {
                GraphicalInterface.logManager.logError("must be within [0,1]" , 3000 );
                return;
            }

            this.Callback.setOperationInSlot( new RangeOperator(k) , slotIndex ,
                    "Range(>="+ k + ")");

        }
        catch ( Exception e )
        {
            GraphicalInterface.logManager.logError("incorrect format" , 3000 );
        }
    }
}
