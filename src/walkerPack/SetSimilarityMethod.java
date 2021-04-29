package walkerPack;

public abstract class SetSimilarityMethod extends SimilarityMeasure
{
    public abstract double getSimilarity(SetSequence seq, SetSequence otherSeq) ;
}
