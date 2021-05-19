package OperationsWrappers;

import GUICode.CallablePaneState;
import SearchGroupOperations.PearsonCC;

public class PearsonGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new PearsonCC() , slotIndex , "Pearson");
    }
}
