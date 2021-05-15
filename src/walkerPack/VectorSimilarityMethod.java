package walkerPack;

public abstract class VectorSimilarityMethod extends SimilarityMeasure
{
    public abstract TimeNSimilarity getSimilarity( VectorSequence seq , VectorSequence otherSeq ) ;
}
