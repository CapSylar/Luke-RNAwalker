package walkerPack;

public abstract class SetSimilarityMethod extends SimilarityMeasure
{
    public abstract TimeNSimilarity getSimilarity(SetSequence seq, SetSequence otherSeq) ;
}
