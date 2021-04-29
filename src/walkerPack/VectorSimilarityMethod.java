package walkerPack;

public abstract class VectorSimilarityMethod extends SimilarityMeasure
{
    public abstract double getSimilarity( VectorSequence seq , VectorSequence otherSeq ) ;
}
