package walkerPack;

public class SequenceBlock
{
    // TODO: too much connection between SearchGroup and Block , could cause problems in the future

    // Block houses the sequence, the set and the vector corresponding to the sequence
    // internal representation used

    private Sequence NativeSequence;
    private SetSequence SequenceAsSet ;
    private VectorSequence SequenceAsVector;
    public double lastSimilarityValue; // used for sorting or trimming the current collection after applying some operation

    public SequenceBlock(Sequence nativeSequence )
    {
        NativeSequence = nativeSequence;
        SequenceAsSet = new SetSequence(NativeSequence.getSequence());
        lastSimilarityValue = -1; // not yet computed
    }

    public void process( int collectionSize , int[] termCollectionCount , boolean useTF , boolean useIDF ) // TODO: only used to recompute the vectors, change the name !
    {
        if ( useIDF )
            SequenceAsVector = new VectorSequence(NativeSequence.getSequence() , collectionSize , termCollectionCount , useTF );
        else
            SequenceAsVector = new VectorSequence(NativeSequence.getSequence());
    }

    public SetSequence getSequenceAsSet()
    {
        return SequenceAsSet;
    }

    public VectorSequence getSequenceAsVector()
    {
        return SequenceAsVector;
    }

    public Sequence getSequence()
    {
        return NativeSequence;
    }

    public long getSetPreProcessingTime ()
    {
        return SequenceAsSet.getPreprocessTime();
    }

    public long getVectorPreProcessingTime ()
    {
        return SequenceAsVector.getPreprocessTime();
    }
}
