package SearchGroupOperations;

import walkerPack.SearchGroup;
import walkerPack.SearchGroupOperation;

public abstract class SelectionOperator extends SearchGroupOperation
{
    public abstract void ApplyOperator( SearchGroup group );
}
