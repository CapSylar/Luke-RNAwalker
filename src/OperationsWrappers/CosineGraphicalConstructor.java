package OperationsWrappers;

import GUICode.CallablePaneState;
import SearchGroupOperations.CosineSimilarity;

public class CosineGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new CosineSimilarity() , slotIndex , "Cosine");
    }
}
