package walkerPack;

// vector representation of an RNA sequence
public class SetSequence
{
    // we might have to store many of these in memory
    // avoid using big DSs here

    // 9 nucleotides:  G A U C R M S V N
    // store them in array , with index starting from 0 as specified

    public int nucleotides[] = new int[9];

    public SetSequence( String sequence )
    {
        preprocess( sequence );   // cutup the sequence and count them
    }

    public void preprocess ( String sequence )
    {
        for ( int i = 0 ; i < sequence.length() ; ++i )
        {
            switch (sequence.charAt(i))
            {
                //TODO: not cleanest way to do it, but performant
                case 'G':
                    ++nucleotides[0];
                    break;
                case 'A':
                    ++nucleotides[1];
                    break;
                case 'U':
                    ++nucleotides[2];
                    break;
                case 'C':
                    ++nucleotides[3];
                    break;
                case 'R':
                    ++nucleotides[4];
                    break;
                case 'M':
                    ++nucleotides[5];
                    break;
                case 'S':
                    ++nucleotides[6];
                    break;
                case 'V':
                    ++nucleotides[7];
                    break;
                case 'N':
                    ++nucleotides[8];
                    break;
            }
        }
    }

    public double Jaccard ( SetSequence otherSet )
    {
        // apply jaccard = A inter B / A union B

        double sim = this.getIntersection( otherSet );
        return sim / ( this.getModule() + otherSet.getModule() - sim ) ;
    }

    public double DiceCoefficient ( SetSequence otherSet )
    {
        // apply dice = 2 * (A inter B) / |A| + |B|

        double sim = this.getIntersection( otherSet );
        return 2*sim / ( this.getModule() + otherSet.getModule() ) ;
    }

    public int getModule()
    {
        int toReturn = 0 ;
        for ( int i = 0 ; i < this.nucleotides.length ; ++i )
            toReturn += this.nucleotides[i];

        return toReturn;
    }

    public int getIntersection( SetSequence otherSet )
    {
        int toReturn = 0;
        for ( int i = 0 ; i < this.nucleotides.length ; ++i )
        {
            toReturn += Math.min( this.nucleotides[i] , otherSet.nucleotides[i] );
        }

        return toReturn;
    }
}

