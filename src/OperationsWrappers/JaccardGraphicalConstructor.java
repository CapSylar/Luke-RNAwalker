package OperationsWrappers;

import GUICode.SearchEnginePaneState;
import SearchGroupOperations.JaccardCoefficient;

public class JaccardGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(SearchEnginePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new JaccardCoefficient() , slotIndex , "Jaccard");
    }
}
