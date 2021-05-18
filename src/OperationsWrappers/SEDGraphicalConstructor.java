package OperationsWrappers;

import GUICode.SearchEnginePaneState;
import walkerPack.SED;

public class SEDGraphicalConstructor implements GraphicalConstructor
{
    @Override
    public void Construct(SearchEnginePaneState callback, int slotIndex)
    {
        callback.setOperationInSlot( new SED() , slotIndex , "SED");
    }
}
