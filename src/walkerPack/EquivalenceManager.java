package walkerPack;

import java.util.*;

public class EquivalenceManager
{
    static HashMap<Character,HashMap<Character,Double>> NuclSimilarityMap = new HashMap<>();
    static HashMap<Character,HashSet<Character>> NuclSets = new HashMap<>() ;
    static HashMap<Character,Integer> Mapper = new HashMap<>();

    public static void initManager()
    {
        PopulateSets();
        InitUpdateCostMap();
        InitMapper();
    }

    private static void InitMapper() // TODO: may not be the best place for it, reconsider this decision
    {
        // this is used to map nucl to their correct order in an array for example
        // 9 nucleotides:  G A U C R M S V N in this order
        String all = "GAUCRMSVN" ;
        for ( int i = 0 ; i < all.length() ; ++i )
        {
            Mapper.put(all.charAt(i) , i);
        }
    }

    private static void PopulateSets()
    {
        NuclSets.put('A' , new HashSet<>() );
        NuclSets.get('A').addAll(Arrays.asList('A') ) ;

        NuclSets.put('G' , new HashSet<>() );
        NuclSets.get('G').addAll(Arrays.asList('G')) ;

        NuclSets.put('C' , new HashSet<>() );
        NuclSets.get('C').addAll(Arrays.asList('C')) ;

        NuclSets.put('U' , new HashSet<>() );
        NuclSets.get('U').addAll(Arrays.asList('U')) ;

        NuclSets.put('R' , new HashSet<>() );
        NuclSets.get('R').addAll(Arrays.asList('G' , 'A' )) ;

        NuclSets.put('M' , new HashSet<>() );
        NuclSets.get('M').addAll(Arrays.asList('A' , 'C' )) ;

        NuclSets.put('S' , new HashSet<>() );
        NuclSets.get('S').addAll(Arrays.asList('G' , 'C' )) ;

        NuclSets.put('V' , new HashSet<>() );
        NuclSets.get('V').addAll(Arrays.asList('G' , 'A' , 'C' )) ;

        NuclSets.put('N' , new HashSet<>() );
        NuclSets.get('N').addAll(Arrays.asList('G' , 'A' , 'U' , 'C')) ;
    }

    private static void InitUpdateCostMap ()
    {
        NuclSimilarityMap.put('G' , new HashMap<>());
        NuclSimilarityMap.put('U' , new HashMap<>());
        NuclSimilarityMap.put('A' , new HashMap<>());
        NuclSimilarityMap.put('C' , new HashMap<>());

        NuclSimilarityMap.put('R' , new HashMap<>());
        NuclSimilarityMap.put('M' , new HashMap<>());
        NuclSimilarityMap.put('S' , new HashMap<>());
        NuclSimilarityMap.put('V' , new HashMap<>());
        NuclSimilarityMap.put('N' , new HashMap<>());
    }

    public static double getNormalizedDistance(char old_nucl , char new_nucl ) // returns cost of update ->
    {
        return 1 - getSimilarity( old_nucl , new_nucl ) ;
    }

    public static double getSimilarity ( char old_nucl , char new_nucl )
    {
        if ( old_nucl == new_nucl )
            return 1 ; // equal sim = 1

        if ( NuclSimilarityMap.get(old_nucl).containsKey(new_nucl)) // check if already cached
        {
            return NuclSimilarityMap.get(old_nucl).get(new_nucl) ;
        }

        // calculate the requested cost and then store it thus avoiding the need to recalculate

        HashSet<Character> intersection = new HashSet<>( NuclSets.get(old_nucl) ) ;
        HashSet<Character> union = new HashSet<>( NuclSets.get(old_nucl)) ;

        union.addAll( NuclSets.get(new_nucl)) ;
        intersection.retainAll( NuclSets.get(new_nucl));

        double similarity = (intersection.size() / ( float )( NuclSets.get(old_nucl).size() )) *
                (intersection.size() / ( float )( NuclSets.get(new_nucl).size() ));

        // sim = probability first symbol = X * probability second symbol = X

        NuclSimilarityMap.get(old_nucl).put(new_nucl , similarity);

        return similarity ;
    }

    public static int NuclMapper ( char Nucl )
    {
        return Mapper.get( Nucl );
    }
}
