package SearchGroupOperations;

import walkerPack.VectorSequence;

public class CosineSimilarity extends VectorSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity(VectorSequence seq, VectorSequence otherSeq)
    {
        long start = System.nanoTime();
        double sim = seq.getDotProduct( otherSeq ) /  ( seq.getModule() * otherSeq.getModule() )  ;

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
