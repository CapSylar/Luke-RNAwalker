package walkerPack;

public class DiceCoefficient extends SetSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity( SetSequence seq, SetSequence otherSeq )
    {
        long start = System.nanoTime();
        // Dice = 2 * (A inter B) / |A| + |B|
        double sim = (2 * seq.getIntersection( otherSeq )) / ( seq.getModule() + otherSeq.getModule() ) ;
        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
