package walkerPack;

import java.util.HashMap;

public class EquivalenceManager
{
    HashMap<Character,String> NuclEquivs;

    public EquivalenceManager()
    {
        this.InitHashMap();
    }

    private void InitHashMap ()
    {
        this.NuclEquivs = new HashMap<>();

        this.NuclEquivs.put('R' , "RGA" ) ;
        this.NuclEquivs.put('M' , "MAC" ) ;
        this.NuclEquivs.put('S' , "SGC" ) ;
        this.NuclEquivs.put('V' , "VGAC" ) ;
        this.NuclEquivs.put('N' , "NGUAC" ) ;
        this.NuclEquivs.put('A' , "A" ) ;
        this.NuclEquivs.put('G' , "G" ) ;
        this.NuclEquivs.put('U' , "U" ) ;
        this.NuclEquivs.put('C' , "C" ) ;
    }

    public Boolean isEquivalent(char old_v , char new_v )
    {
        return !(this.NuclEquivs.get(old_v).indexOf(new_v) == -1 );
    }
}
