package walkerPack;

public class PearsonCC extends VectorSimilarityMethod
{
    @Override
    public double getSimilarity( VectorSequence seq , VectorSequence otherSeq )
    {
        // Pearson correlation coefficient ( A , B ) = sigma { ( Ai - avgA ) * ( Bi - avgB ) }/ sqrt ( sigma{(Ai - avgA)^2} * sigma{(Bi - avgB)^2} )
        return seq.getAveragedDotProduct( otherSeq ) / ( seq.getAveragedModule() * otherSeq.getAveragedModule() );
    }
}
