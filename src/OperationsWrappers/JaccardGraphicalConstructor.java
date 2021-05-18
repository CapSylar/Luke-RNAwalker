package OperationsWrappers;

import GUICode.CallablePaneState;
import GUICode.SearchEnginePaneState;
import SearchGroupOperations.JaccardCoefficient;

public class JaccardGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new JaccardCoefficient() , slotIndex , "Jaccard");
    }
}
