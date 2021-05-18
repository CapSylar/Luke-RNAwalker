package OperationsWrappers;

import GUICode.CallablePaneState;
import GUICode.SearchEnginePaneState;

public class NoOperationConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
    {
        // No Operation
        callback.setOperationInSlot( null , slotIndex , "Skip Slot");
    }
}
