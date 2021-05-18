package SearchGroupOperations;

import walkerPack.SetSequence;
import walkerPack.SimilarityMeasure;

public abstract class SetSimilarityMethod extends SimilarityMeasure
{
    public abstract TimeNSimilarity getSimilarity(SetSequence seq, SetSequence otherSeq) ;
}
