package walkerPack;

public class SED extends SimilarityMeasure
{
    public double getSimilarity ( Sequence first , Sequence second )
    {
        return getSimilarity( first , second , null );
    }

    public double getSimilarity ( Sequence first , Sequence second , EditScriptSequence currentScript )
    {
        String StringSequence1 = first.getSequence() ;
        String StringSequence2 = second.getSequence() ;

        float dp[][] = new float[StringSequence1.length()+1][StringSequence2.length()+1] ;
        byte backtrack[][] = new byte [StringSequence1.length()+1][StringSequence2.length()+1] ;

        // [2] : one for edit distance , 1: to note were we came from , 0 from delete, 1 from update
        // ,2 from insert ( 0-from up , 1-from diagonal , 2-from left )

        dp[0][0] = 0 ;

        for ( int i = 1 ; i <= StringSequence2.length() ; ++i )
        {
            dp[0][i] = i ;
            backtrack[0][i] = 2; // we came from the left cell
        }

        for ( int i = 1 ; i <= StringSequence1.length() ; ++i )
        {
            dp[i][0] = i ;
            backtrack[i][0] = 0 ; // we came from the up cell
        }

        EquivalenceManager equivalence = new EquivalenceManager();

        for ( int i = 1 ; i <= StringSequence1.length() ; ++i )
        {
            for (int j = 1; j <= StringSequence2.length(); ++j)
            {
                // we have to note were we came from , calc each thing separately

                int costUpdate = 1 , costDelete = 1, costInsert = 1;

                float update = dp[i - 1][j - 1] + equivalence.getUpdateWeight(StringSequence1.charAt(i - 1), StringSequence2.charAt(j - 1) ) * costUpdate  ;
                float delete = dp[i - 1][j] + costDelete;
                float insert = dp[i][j - 1] + costInsert;

                /* by changing the order of the comparisons we could prioritize certain operations later, we could pick the ops
                 * that are the easiest on the program in terms of speed */

                dp[i][j] = Math.min(update, Math.min(delete, insert));

                if (dp[i][j] == update)
                    backtrack[i][j] = 1;
                else if (dp[i][j] == delete)
                    backtrack[i][j] = 0 ;
                else
                    backtrack[i][j] = 2;
            }
        }

        double distance = 1- ((dp[StringSequence1.length()][StringSequence2.length()])/(StringSequence1.length() + StringSequence2.length())) ;

        if ( currentScript == null )
        {
            return distance;
        }

        int i = StringSequence1.length();
        int j = StringSequence2.length();

        while ( i != 0 || j != 0 )
            switch ( backtrack[i][j] )
            {
                case 0: // delete operation
                    currentScript.pushFront( new DeleteOperation( i-1 , StringSequence1.charAt(i-1) , j ));
                    --i ;
                    break ;
                case 1: // update operation
                {
                    char oldChar = StringSequence1.charAt(i - 1) ;
                    char newChar = StringSequence2.charAt(j - 1) ;
                    // add even if they are equivalent ( equivalency is only one way !)
                    if ( oldChar != newChar ) // do not include if characters are the same ( include even if cost 0 )
                        currentScript.pushFront(new UpdateOperation(i - 1, StringSequence2.charAt(j - 1),
                                StringSequence1.charAt(i - 1), j - 1));
                    --i;
                    --j;
                }
                break ;
                case 2: // insert operation
                    currentScript.pushFront( new InsertOperation( i , StringSequence2.charAt(j-1) , j-1 ));
                    --j ;
                    break ;
            }

        return distance;
    }
}
