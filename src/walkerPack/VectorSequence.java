package walkerPack;

public class VectorSequence
{
    // 9 nucleotides:  G A U C R M S V N
    // store them in array , with index starting from 0 as specified

    private long preprocessTime;

    public long getPreprocessTime()
    {
        return this.preprocessTime;
    }

    double nuclWeights[] ;

    public VectorSequence( String sequence )
    {
        long start = System.nanoTime();
        // use only Term Frequency in this case TF ( Ti , Document )

        this.nuclWeights = new double[9];
        double tf[] = Sequence.countTermFreq( sequence );

        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            this.nuclWeights[i] = tf[i];
        }

        this.preprocessTime = System.nanoTime() - start;
    }

    public VectorSequence ( String sequence , int collectionCount , int[] termCollectionCount ,  boolean includeTF )
    {
        // use IDF of term in all cases , boolean to use Term Frequency or not
        // termCollectionCount is the number of docs in C containing at least one occurrence of term Ti = DF(Ti,C)
        // termCollectionCount is assumed to be ordered in the same way as nuclWeights

        // populate array with IDF

        long start = System.nanoTime();

        this.nuclWeights = new double[9];

        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            if ( termCollectionCount[i] > 0 )
                this.nuclWeights[i] = 1 + (Math.log10(collectionCount/(double) termCollectionCount[i] ));
        }

        if ( includeTF ) // use Term frequency as well
        {
            double[] tf = Sequence.countTermFreq( sequence );

            for ( int i = 0 ; i < tf.length ; ++i )
            {
                this.nuclWeights[i] *= tf[i];
            }
        }

        this.preprocessTime = System.nanoTime() - start;
    }

    public double getModule()
    {
        // module of a vector = sqrt ( dimension1^2 + dimension2^2 ... )
        double module = 0;
        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            module += this.nuclWeights[i] * this.nuclWeights[i];
        }

        return Math.sqrt(module);
    }

    public double getAveragedModule()
    {
        double module = 0;

        double avgCurrent = this.getAverage();
        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            module += (this.nuclWeights[i] - avgCurrent) * (this.nuclWeights[i] - avgCurrent) ;
        }

        return Math.sqrt(module);
    }

    public double getDotProduct(VectorSequence otherSeq )
    {
        double product = 0;

        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            product += this.nuclWeights[i] * otherSeq.nuclWeights[i] ;
        }

        return product;
    }

    public double getAveragedDotProduct(VectorSequence otherSeq )
    {
        double product = 0;

        double avgCurrent = this.getAverage();
        double avgOther = otherSeq.getAverage();

        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            product += ((this.nuclWeights[i] - avgCurrent) * (otherSeq.nuclWeights[i] - avgOther)) ;
        }

        return product;
    }

    public double getAverage()
    {
        // average of the values over all the dimensions
        double average = 0;

        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
            average += this.nuclWeights[i];

        return average / this.nuclWeights.length;
    }

    @Override
    public String toString()
    {
        // 9 nucleotides:  G A U C R M S V N
        return "VectorSequence{" +
                "nuclWeights=" + "G:" + nuclWeights[0] +
                " A:" + nuclWeights[1] +
                " U:" + nuclWeights[2] +
                " C:" + nuclWeights[3] +
                " R:" + nuclWeights[4] +
                " M:" + nuclWeights[5] +
                " S:" + nuclWeights[6] +
                " V:" + nuclWeights[7] +
                " N:" + nuclWeights[8] +
                '}';
    }
}
