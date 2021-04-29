package walkerPack;

import java.util.ArrayList;

public class Starter
{
    public static void main(String args[])
    {


    // load settings manager

    EquivalenceManager.initManager();
        //        SettingsManager manager = SettingsManager.load() ; // load settings into app
    //
    //        GraphicalInterface.currentManager = manager ; // bind manager
    //        GraphicalInterface.main(args); // launch graphical interface

        ArrayList<Sequence> seqlist = new ArrayList<>();

        seqlist.add(new Sequence("CACACAACA"));
        seqlist.add(new Sequence("CAGGCAACA"));
        seqlist.add(new Sequence("CARRRACA"));
        seqlist.add(new Sequence("CAAAAACA"));
        seqlist.add(new Sequence("CACCACACCA"));
        seqlist.add(new Sequence("GACRNNR"));

        SearchGroup group = new SearchGroup(seqlist);

        System.out.println(group);

        group.rankUsingSimilarity( new Sequence("CCCC") , new JaccardCoefficient() );

        System.out.println(group);

        group.filterSelection( new RangeOperator(0.2 ));

        group.rankUsingSimilarity( new Sequence("CCCC") , new PearsonCC() );

        System.out.println(group);

        group.rankUsingSimilarity( new Sequence("CCCC") , new SED() );

        System.out.println(group);
    }
}
