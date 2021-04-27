package walkerPack;

import java.util.Arrays;

public class VectorSequence
{
    // 9 nucleotides:  G A U C R M S V N
    // store them in array , with index starting from 0 as specified

    double nuclWeights[] ;

    public VectorSequence( String sequence )
    {
        // use only Term Frequency in this case TF ( Ti , Document )

        this.nuclWeights = new double[9];
        int tf[] = countTF( sequence );

        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            this.nuclWeights[i] = tf[i];
        }
    }

    public VectorSequence ( String sequence , int collectionCount , int[] termCollectionCount ,  boolean includeTF )
    {
        // use IDF of term in all cases , boolean to use Term Frequency or not
        // termCollectionCount is the number of docs in C containing at least one occurrence of term Ti = DF(Ti,C)
        // termCollectionCount is assumed to be ordered in the same way as nuclWeights

        // populate array with IDF

        this.nuclWeights = new double[9];

        for ( int i = 0 ; i < this.nuclWeights.length ; ++i )
        {
            this.nuclWeights[i] = Math.log10(collectionCount/(double) termCollectionCount[i] );
        }

        if ( includeTF ) // use Term frequency as well
        {
            int[] tf = countTF( sequence );

            for ( int i = 0 ; i < tf.length ; ++i )
            {
                this.nuclWeights[i] *= tf[i];
            }
        }
    }

    private int[] countTF ( String sequence )
    {
        int tf[] = new int [9];

        for ( int i = 0 ; i < sequence.length() ; ++i )
        {
            switch (sequence.charAt(i))
            {
                //TODO: not cleanest way to do it, but performant
                case 'G':
                    ++tf[0];
                    break;
                case 'A':
                    ++tf[1];
                    break;
                case 'U':
                    ++tf[2];
                    break;
                case 'C':
                    ++tf[3];
                    break;
                case 'R':
                    ++tf[4];
                    break;
                case 'M':
                    ++tf[5];
                    break;
                case 'S':
                    ++tf[6];
                    break;
                case 'V':
                    ++tf[7];
                    break;
                case 'N':
                    ++tf[8];
                    break;
            }
        }

        return tf;
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

    public double getCosineSimilarity(VectorSequence otherSeq )
    {
        // cosine similarity ( A , B )  = A dot B / ( |A| * |B| )

        return getDotProduct( otherSeq ) / ( this.getModule() * otherSeq.getModule() ) ;
    }

    public double getPearsonCC(VectorSequence otherSeq )
    {
        // Pearson correlation coefficient ( A , B ) = sigma { ( Ai - avgA ) * ( Bi - avgB ) }/ sqrt ( sigma{(Ai - avgA)^2} * sigma{(Bi - avgB)^2} )

        return getAveragedDotProduct( otherSeq ) / ( this.getAveragedModule() * otherSeq.getAveragedModule() );
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
            product += (this.nuclWeights[i] - avgCurrent) * (otherSeq.nuclWeights[i] - avgOther) ;
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
