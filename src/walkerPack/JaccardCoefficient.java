package walkerPack;

public class JaccardCoefficient extends SetSimilarityMethod
{
    @Override
    public double getSimilarity(SetSequence seq, SetSequence otherSeq)
    {
        // apply jaccard = A inter B / A union B

        double intersection = seq.getAugmentedIntersection( otherSeq );
        return intersection / ( seq.getModule() + otherSeq.getModule() - intersection ) ;
    }
}
