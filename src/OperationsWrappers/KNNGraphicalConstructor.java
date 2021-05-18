package OperationsWrappers;

import GUICode.CallablePaneState;
import GUICode.KNNPaneController;
import GUICode.SearchEnginePaneState;
import SearchGroupOperations.KNNOperator;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import walkerPack.GraphicalInterface;

public class KNNGraphicalConstructor implements GraphicalConstructor
{
    public static KNNPaneController controller;

    private Stage current;
    private CallablePaneState Callback;
    private int slotIndex;

    @Override
    public void Construct(CallablePaneState Callback , int slotIndex )
    {
        current = new Stage();
        current.initStyle(StageStyle.UNDECORATED);
        current.initModality(Modality.APPLICATION_MODAL);
        current.initOwner(GraphicalInterface.PrimaryStage);
        current.setScene(GraphicalInterface.KNNPane);
        current.show();

        this.Callback = Callback;
        this.slotIndex = slotIndex;

        // setup callback for the KNN Pane controller
        controller.setupCallback(this);
    }

    public void OnButtonPressed( String value ) // called by GUI constructor
    {
        try
        {
            current.close(); // close window, even if user input is erroneous
            int k = Integer.parseInt(value);

            this.Callback.setOperationInSlot( new KNNOperator(k), slotIndex , "KNN("+k+")" ); // construct k and give it back
            System.out.println("from KNNGraphicalContructor , received from Controller: " + value );
        }
        catch ( Exception e )
        {
            //TODO: handle exception properly
        }
    }
}
