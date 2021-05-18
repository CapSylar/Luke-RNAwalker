package OperationsWrappers;

import GUICode.SearchEnginePaneState;
import SearchGroupOperations.PearsonCC;

public class PearsonGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(SearchEnginePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new PearsonCC() , slotIndex , "Peason");
    }
}
