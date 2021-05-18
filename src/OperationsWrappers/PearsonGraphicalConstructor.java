package OperationsWrappers;

import GUICode.CallablePaneState;
import GUICode.SearchEnginePaneState;
import SearchGroupOperations.PearsonCC;

public class PearsonGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new PearsonCC() , slotIndex , "Peason");
    }
}
