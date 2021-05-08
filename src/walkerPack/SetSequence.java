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
            //TODO: not cleanest way to do it, but performant O(1)
            ++nucleotides[EquivalenceManager.NuclMapper(sequence.charAt(i))] ;
        }
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

