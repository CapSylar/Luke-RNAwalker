package SearchGroupOperations;

import walkerPack.SetSequence;

public class JaccardCoefficient extends SetSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity(SetSequence seq, SetSequence otherSeq)
    {
        // Jaccard = A inter B / A union B
        long start = System.nanoTime();

        double sim = seq.getIntersection( otherSeq );
        sim = sim / ( seq.getModule() + otherSeq.getModule() - sim ) ;

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
