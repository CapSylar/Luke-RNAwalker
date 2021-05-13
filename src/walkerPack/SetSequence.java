package walkerPack;

// vector representation of an RNA sequence
public class SetSequence
{
    private long preprocessTime;

    public long getPreprocessTime()
    {
        return this.preprocessTime;
    }
    // we might have to store many of these in memory
    // avoid using big DSs here

    // 9 nucleotides:  G A U C R M S V N
    // store them in array , with index starting from 0 as specified

    public int nucleotides[] = new int[9];

    public SetSequence( String sequence )
    {
        this.preprocessTime = preprocess( sequence );   // cutup the sequence and count them
    }

    private long preprocess ( String sequence ) // returns time it took to preprocess
    {
        long startTimeNano = System.nanoTime();
        for ( int i = 0 ; i < sequence.length() ; ++i )
        {
            //TODO: not cleanest way to do it, but performant O(1)
            ++nucleotides[EquivalenceManager.NuclMapper(sequence.charAt(i))] ;
        }
        return System.nanoTime() - startTimeNano;
    }

    public int getModule()
    {
        int toReturn = 0 ;
        for ( int i = 0 ; i < this.nucleotides.length ; ++i )
            toReturn += this.nucleotides[i];

        return toReturn;
    }

    public double getAugmentedIntersection(SetSequence otherSet)
    {
        // TODO: can be optimized to skip some loops where nucleotides are zero
        double toReturn = 0;
        char[] let ={'G','A','U','C','R','M','S','V','N'};

        for ( int i = 0 ; i < this.nucleotides.length ; ++i )
            for(int j = 0 ; j < this.nucleotides.length ; ++j )
                toReturn += Math.min(this.nucleotides[i], otherSet.nucleotides[j]) * EquivalenceManager.getSimilarity(let[i], let[j]);

        return toReturn;
    }


    public double getIntersection( SetSequence otherSet )
    {
        double toReturn = 0;

        for ( int i = 0 ; i < this.nucleotides.length ; ++i )
        {
            toReturn += Math.min( this.nucleotides[i] , otherSet.nucleotides[i] );
        }

        return toReturn;
    }
}

