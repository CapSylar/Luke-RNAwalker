package OperationsWrappers;

import GUICode.CallablePaneState;
import GUICode.SearchEnginePaneState;
import SearchGroupOperations.DiceCoefficient;

public class DiceGraphicalConstructor implements GraphicalConstructor
{
    private CallablePaneState Callback;
    private int slotIndex;

    @Override
    public void Construct(CallablePaneState callback, int slotIndex)
    {
        this.Callback = callback;
        this.slotIndex = slotIndex;

        // nothing to do for the dice measure, just call it back right away
        this.Callback.setOperationInSlot(new DiceCoefficient(), slotIndex, "Dice");
    }
}
