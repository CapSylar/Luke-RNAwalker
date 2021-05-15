package walkerPack;

import java.util.ArrayList;

public class KNNOperator extends SelectionOperator
{
    private int k;

    public KNNOperator ( int k )
    {
        this.k = k;
    }

    @Override
    public void ApplyOperator(SearchGroup group)
    {
        // K-nearest neighborhood operator returns the set of k neighboring documents located nearest to a query Q using a distance function d

        ArrayList<SequenceBlock> collection = group.collection;

        // collection is sorted, get first k
        // TODO: check if there is a faster way to do this

        while ( collection.size() > k )
        {
            collection.remove(k);
        }
    }
}
