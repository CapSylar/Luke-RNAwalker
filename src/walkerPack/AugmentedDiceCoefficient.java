package walkerPack;

public class AugmentedDiceCoefficient extends SetSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity( SetSequence seq, SetSequence otherSeq )
    {
        // DICE = 2 * (A inter B) / |A| + |B| taking into consideration ambiguity symbols
        long start = System.nanoTime();
        double sim = (2 * seq.getAugmentedIntersection( otherSeq )) / ( seq.getModule() + otherSeq.getModule() ) ;

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
