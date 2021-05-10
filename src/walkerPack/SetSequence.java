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
            EquivalenceManager em = new EquivalenceManager();
            em.initManager();
            //TODO: not cleanest way to do it, but performant O(1)
            ++nucleotides[em.NuclMapper(sequence.charAt(i))] ;
        }
    }

    public int getModule()
    {
        int toReturn = 0 ;
        for ( int i = 0 ; i < this.nucleotides.length ; ++i )
            toReturn += this.nucleotides[i];

        return toReturn;
    }

    public float getIntersection( SetSequence otherSet ) // kamen hay return double
    {
        float toReturn = 0;   // ta tezbat ghayir hole to double

         //khoure implementation
        float toAdd = 0;
        char[] let ={'G','A','U','C','R','M','S','V','N'};
        EquivalenceManager eq = new EquivalenceManager();
        eq.initManager();
        float ans;
        for ( int i = 0 ; i < this.nucleotides.length ; ++i ) {
            toAdd = 0;
            for(int j=0; j < this.nucleotides.length ; j++ ) {
                float res = eq.getUpdateCost(let[i], let[j]);
                //System.out.println(res);
                if(res == ((float)0)){
                    ans = (float)1;
                }
                if(res == ((float)1)){
                    ans = (float)0;
                }
                else {
                    ans = res;
                }
                toAdd += (float)Math.min(this.nucleotides[i], otherSet.nucleotides[j]) * ans;
            }
            toReturn += toAdd;
        }
        return toReturn;

        //done

//        for ( int i = 0 ; i < this.nucleotides.length ; ++i )
//        {
//            toReturn += Math.min( this.nucleotides[i] , otherSet.nucleotides[i] );
//        }
//
//        return toReturn;
    }
}

