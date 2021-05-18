package SearchGroupOperations;


import walkerPack.SetSequence;

public class AugmentedJaccardCoefficient extends SetSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity(SetSequence seq, SetSequence otherSeq)
    {
        // apply jaccard = A inter B / A union B taking into consideration ambiguity symbols
        long start = System.nanoTime();
        double intersection = seq.getAugmentedIntersection( otherSeq );
        double sim = intersection / ( seq.getModule() + otherSeq.getModule() - intersection ) ;

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
