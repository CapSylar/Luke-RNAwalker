package SearchGroupOperations;

import walkerPack.VectorSequence;

public class PearsonCC extends VectorSimilarityMethod
{
    @Override
    public TimeNSimilarity getSimilarity(VectorSequence seq , VectorSequence otherSeq )
    {
        // Pearson correlation coefficient ( A , B ) = sigma { ( Ai - avgA ) * ( Bi - avgB ) }/ sqrt ( sigma{(Ai - avgA)^2} * sigma{(Bi - avgB)^2} )
        long start = System.nanoTime();
        double sim = seq.getAveragedDotProduct( otherSeq ) / ( seq.getAveragedModule() * otherSeq.getAveragedModule() );

        return new TimeNSimilarity( System.nanoTime() - start , sim );
    }
}
