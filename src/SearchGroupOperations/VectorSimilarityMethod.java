package SearchGroupOperations;

import walkerPack.SimilarityMeasure;
import walkerPack.VectorSequence;

public abstract class VectorSimilarityMethod extends SimilarityMeasure
{
    public abstract TimeNSimilarity getSimilarity(VectorSequence seq , VectorSequence otherSeq ) ;
}
