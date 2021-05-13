package walkerPack;

public class CosineSimilarity extends VectorSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity(VectorSequence seq, VectorSequence otherSeq)
    {
        long start = System.nanoTime();
        double sim = seq.getDotProduct( otherSeq ) /  ( seq.getModule() * otherSeq.getModule() )  ;

        // if one of the two has zero for all waits meaning , happens only in the case where we use IDF, just put sim = 0

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
