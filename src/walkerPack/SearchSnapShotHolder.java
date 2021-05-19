package walkerPack;

import SearchGroupOperations.SelectionOperator;

import java.util.ArrayList;

public class SearchSnapShotHolder
{
    // intended as a sort of wrapper for SearchGroup, will take a list of measures to take and record the state of Searchgroup
    // at the end of every applied measure or filtering, making it able to view the results after every stage and the time it took

    private SearchGroup group;
    private ArrayList<SearchGroupSnapshot> SnapShots;

    public SearchSnapShotHolder(SearchGroup group)
    {
        this.group = group;
    }

    public long applyOperationStack( Sequence query , SearchGroupOperation stack[] )
    {
        SnapShots = new ArrayList<>();
        long totalTime = 0 ;
        // apply operations on the stack on by one and create a 'snapshot' each time
        for ( int i = 0 ; i < stack.length; ++i )
        {
            SearchGroupOperation currentOperation = stack[i];

            // if null, skip it , but create a snapshot anyways, just put a pointer to the last snapshot
            if ( currentOperation == null )
            {
                SnapShots.add(SnapShots.get(SnapShots.size()-1)); // copy last one, no cost involved
                continue;
            }

            long timeItTook = 0; // time it took for filter selection is zero, we don't need time reporting for it

            if ( currentOperation instanceof SimilarityMeasure )
            {
                timeItTook = this.group.rankUsingSimilarity( query , (SimilarityMeasure) currentOperation ) ;
            }
            else
                this.group.filterSelection((SelectionOperator) currentOperation);

            totalTime += timeItTook;
            SnapShots.add(new SearchGroupSnapshot( this.group.toString() , timeItTook));
        }

        return totalTime;
    }

    public SearchGroupSnapshot getSnapshot ( int i )
    {
        return SnapShots.get(i);
    }
}
