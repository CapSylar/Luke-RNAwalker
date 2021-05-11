package walkerPack;

public class DiceCoefficient extends SetSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity( SetSequence seq, SetSequence otherSeq )
    {
        // apply dice = 2 * (A inter B) / |A| + |B|
        long start = System.nanoTime();
        double sim = 2 * seq.getIntersection( otherSeq ) / ( seq.getModule() + otherSeq.getModule() ) ;

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
