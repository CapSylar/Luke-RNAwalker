package walkerPack;

import java.util.*;

public class EquivalenceManager
{
    static HashMap<Character,HashMap<Character,Float>> UpdateCostMap = new HashMap<>();
    static HashMap<Character,HashSet<Character>> NuclSets = new HashMap<>() ;

    public static void initManager()
    {
        PopulateSets();
        InitUpdateCostMap();
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
        UpdateCostMap.put('G' , new HashMap<>());
        UpdateCostMap.put('U' , new HashMap<>());
        UpdateCostMap.put('A' , new HashMap<>());
        UpdateCostMap.put('C' , new HashMap<>());

        UpdateCostMap.put('R' , new HashMap<>());
        UpdateCostMap.put('M' , new HashMap<>());
        UpdateCostMap.put('S' , new HashMap<>());
        UpdateCostMap.put('V' , new HashMap<>());
        UpdateCostMap.put('N' , new HashMap<>());
    }

    public static float getUpdateWeight(char old_v , char new_v ) // returns cost of update ->
    {
        if ( old_v == new_v )
            return 0 ;

        if ( UpdateCostMap.get(old_v).containsKey(new_v))
        {
            return UpdateCostMap.get(old_v).get(new_v) ;
        }

        // calculate the requested cost and then store it thus avoiding the need to recalculate

        HashSet<Character> intersection = new HashSet<>( NuclSets.get(old_v) ) ;
        HashSet<Character> union = new HashSet<>( NuclSets.get(old_v)) ;

        union.addAll( NuclSets.get(new_v)) ;
        intersection.retainAll( NuclSets.get(new_v));

//        double sim = intersection.size() / ( double ) union.size() ;

        float prob = 1 - (intersection.size() / ( float )( NuclSets.get(old_v).size() )) *
                (intersection.size() / ( float )( NuclSets.get(new_v).size() )) ;

        UpdateCostMap.get(old_v).put(new_v , prob );

        return prob ;
    }
}
