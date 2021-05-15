package walkerPack;

import java.util.ArrayList;

public class SearchSnapShotHolder
{
    // intended as a sort of wrapper for SearchGroup, will take a list of measures to take and record the state of Searchgroup
    // at the end of every applied measure or filtering, making it able to view the results after every stage and the time it took

    private SearchGroup group;
    private ArrayList<SearchGroupSnapshot> current;

    public SearchSnapShotHolder(SearchGroup group )
    {
        this.group = group;
    }

    public void applyOperationStack( Sequence query , ArrayList<SearchGroupOperation> stack )
    {
        current = new ArrayList<>();
        // apply operations on the stack on by one and create a 'snapshot' each time
        for ( int i = 0 ; i < stack.size() ; ++i )
        {
            SearchGroupOperation currentOperation = stack.get(i);
            long timeItTook = 0; // time it took for filter selection is zero, we don't need time reporting for it

            if ( currentOperation instanceof SimilarityMeasure )
            {
                timeItTook = this.group.rankUsingSimilarity( query , (SimilarityMeasure) currentOperation ) ;
            }
            else
                this.group.filterSelection((SelectionOperator) currentOperation);

            current.add(new SearchGroupSnapshot( this.group.toString() , timeItTook));
        }
    }

    public SearchGroupSnapshot getSnapshot ( int i )
    {
        return current.get(i);
    }
}
