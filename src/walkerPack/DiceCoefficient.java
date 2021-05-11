package walkerPack;

public class DiceCoefficient extends SetSimilarityMethod
{
    @Override
    public double getSimilarity( SetSequence seq, SetSequence otherSeq )
    {
        // Dice = 2 * (A inter B) / |A| + |B|

        double sim = seq.getIntersection( otherSeq );
        return 2*sim / ( seq.getModule() + otherSeq.getModule() ) ;
    }
}
