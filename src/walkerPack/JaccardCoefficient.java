package walkerPack;

public class JaccardCoefficient extends SetSimilarityMethod
{
    @Override
    public double getSimilarity(SetSequence seq, SetSequence otherSeq)
    {

        // apply jaccard = A inter B / A union B

        double sim = seq.getIntersection( otherSeq );
        return sim / ( seq.getModule() + otherSeq.getModule() - sim ) ;
    }
}
