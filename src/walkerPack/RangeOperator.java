package walkerPack;

import java.util.ArrayList;

public class RangeOperator implements SelectionOperator
{
    // TODO: maybe we should specify a distance here rather than a similarity value
    private double similarityLowerBound;

    public RangeOperator( double similarityLowerBound )
    {
        this.similarityLowerBound = similarityLowerBound;
    }

    @Override
    public void ApplyOperator(SearchGroup group)
    {
        // returns set of documents located within a certain range from Query using similarity as distance in this case
        // should not change the ordering of the returned sequences inside the collection

        //TODO: should the operator know all the details of the implementation of the group collection ?, reconsider

        ArrayList<SearchGroup.Block> collection = group.collection;

        for ( int i = 0; i < collection.size() ;) //TOFIX: group collection is always sorted, make the removal faster
        {
            if ( collection.get(i).lastSimilarityValue < this.similarityLowerBound )
                collection.remove(i);
            else
                ++i;
        }

    }
}
