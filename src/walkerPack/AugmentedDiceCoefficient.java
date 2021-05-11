package walkerPack;

public class AugmentedDiceCoefficient extends SetSimilarityMethod
{
    @Override
    public double getSimilarity( SetSequence seq, SetSequence otherSeq )
    {
        // apply dice = 2 * (A inter B) / |A| + |B|

        double sim = seq.getAugmentedIntersection( otherSeq );
        return 2*sim / ( seq.getModule() + otherSeq.getModule() ) ;
    }
}
