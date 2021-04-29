package walkerPack;

public class CosineSimilarity extends VectorSimilarityMethod
{
    @Override
    public double getSimilarity(VectorSequence seq, VectorSequence otherSeq)
    {
        return seq.getDotProduct( otherSeq ) / ( seq.getModule() * otherSeq.getModule() ) ;
    }
}
