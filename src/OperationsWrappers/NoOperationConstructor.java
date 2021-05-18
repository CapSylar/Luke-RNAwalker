package OperationsWrappers;

import GUICode.SearchEnginePaneState;

public class NoOperationConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(SearchEnginePaneState callback, int slotIndex)
    {
        // No Operation
        callback.setOperationInSlot( null , slotIndex , "Skip Slot");
    }
}
