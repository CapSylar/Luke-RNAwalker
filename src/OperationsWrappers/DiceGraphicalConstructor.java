package OperationsWrappers;

import GUICode.SearchEnginePaneState;
import SearchGroupOperations.DiceCoefficient;

public class DiceGraphicalConstructor implements GraphicalConstructor
{
    private SearchEnginePaneState Callback;
    private int slotIndex;

    @Override
    public void Construct(SearchEnginePaneState callback, int slotIndex)
    {
        this.Callback = callback;
        this.slotIndex = slotIndex;

        // nothing to do for the dice measure, just call it back right away
        this.Callback.setOperationInSlot(new DiceCoefficient(), slotIndex, "Dice");
    }
}
