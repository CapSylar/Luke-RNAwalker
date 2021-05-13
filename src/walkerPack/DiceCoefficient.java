package walkerPack;

public class DiceCoefficient extends SetSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity( SetSequence seq, SetSequence otherSeq )
    {
        // Dice = 2 * (A inter B) / |A| + |B|

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
