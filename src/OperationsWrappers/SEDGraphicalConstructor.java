package OperationsWrappers;

import GUICode.CallablePaneState;
import GUICode.SearchEnginePaneState;
import SearchGroupOperations.SED;

public class SEDGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new SED() , slotIndex , "SED");
    }
}
