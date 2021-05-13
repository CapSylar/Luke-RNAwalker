package walkerPack;

import java.util.ArrayList;

public class Starter
{
    public static void main(String args[])
    {
    // load settings manager

    EquivalenceManager.initManager(); // handles all comparison by building a hashMap just once

    //        SettingsManager manager = SettingsManager.load() ; // load settings into app
    //
    //        GraphicalInterface.currentManager = manager ; // bind manager
    //        GraphicalInterface.main(args); // launch graphical interface

        SearchGroup group = SearchGroup.fromXML("test-files/FormattedSequences.xml");

        System.out.println(group);

        long nanoTime = group.rankUsingSimilarity( new Sequence("CCCC") , new JaccardCoefficient() );

        System.out.println("It took " + nanoTime/1000 );
//        System.out.println(group);

        group.filterSelection( new KNNOperator(10 ));

        nanoTime = group.rankUsingSimilarity( new Sequence("ACGCCUCCACGAGUGUCUU") , new SED() );


        System.out.println( "It took " +  nanoTime/1000 );
//        System.out.println(group);
    }
}
