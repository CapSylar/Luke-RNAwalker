package OperationsWrappers;

import GUICode.SearchEnginePaneState;
import SearchGroupOperations.CosineSimilarity;

public class CosineGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(SearchEnginePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new CosineSimilarity() , slotIndex , "Cosine");
    }
}
