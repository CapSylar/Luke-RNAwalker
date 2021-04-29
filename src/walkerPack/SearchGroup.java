package walkerPack;
import java.util.ArrayList;

public class SearchGroup
{
    ArrayList<Block> collection;

    // DEBUG INFO

    private Sequence lastQuery = null ;
    /**
     * SearchGroup is essentially a component holding a collection of sorted RNA Sequences ( Sequences class )
     * Operations such as KNN or range can be applied to the SearchGroup removing some elements
     * Other operations such as sort by similarity using Cosine... will reorder the sequences inside the SearchGroup
     */

    public SearchGroup ( ArrayList<Sequence> sequences )
    {
        this.collection = new ArrayList<>();

        for ( int i = 0 ; i < sequences.size() ; ++i )
        {
            collection.add( new Block( sequences.get(i) ));
        }
    }

    public void rankUsingSimilarity ( Sequence query , SimilarityMeasure measure )
    {
        this.lastQuery = query;
        for ( int i = 0 ; i < collection.size() ; ++i )
        {
            double currentSim = measure.calculateSimilarity( new Block(query) , collection.get(i) );
            collection.get(i).lastSimilarityValue = currentSim;
        }

        sortCollection();
    }

    public void filterSelection(SelectionOperator operator )
    {
        operator.ApplyOperator(this); // apply range on ourselves
    }


    private void sortCollection()
    {
        // sort collection with respect to lastSimilarityValue

        this.collection.sort((o1, o2) ->
        {
            if ( o1.lastSimilarityValue > o2.lastSimilarityValue )
                return -1;
            else if ( o1.lastSimilarityValue == o2.lastSimilarityValue )
                    return 0;
            else
                return 1;
        });
    }


    // TODO: ugly ass thing, reconsider this decision
    public class Block
    {
        // Block houses the sequence, the set and the vector corresponding to the sequence
        // internal representation used

        private Sequence NativeSequence;
        private SetSequence SequenceAsSet ;
        private VectorSequence SequenceAsVector;
        public double lastSimilarityValue; // used for sorting or trimming the current collection after applying some operation

        public Block(Sequence nativeSequence )
        {
            NativeSequence = nativeSequence;
            SequenceAsSet = null;
            SequenceAsVector = null;
            lastSimilarityValue = -1; // not yet computed
        }

        public SetSequence getSequenceAsSet()
        {
            if ( SequenceAsSet == null )
            {
                SequenceAsSet = new SetSequence(NativeSequence.getSequence());
            }

            return SequenceAsSet;
        }

        public VectorSequence getSequenceAsVector()
        {
            if ( SequenceAsVector == null )
            {
                SequenceAsVector = new VectorSequence(NativeSequence.getSequence()); // TODO: for now we only use TF, change it !!!
            }

            return SequenceAsVector;
        }

        public Sequence getSequence()
        {
            return NativeSequence;
        }
    }

    @Override
    public String toString()
    {
        // list the ranked collection with last similarity info
        String builder = "Search Group{ last query used: " + (this.lastQuery != null ? this.lastQuery.getSequence() : "none") + "\n";

        for ( int i = 0 ; i < collection.size() ; ++i )
        {
            builder += "rank "+ i + " : " + collection.get(i).NativeSequence.getSequence() + " " + collection.get(i).lastSimilarityValue + '\n' ;
        }

        return builder + "} \n";
    }
}
