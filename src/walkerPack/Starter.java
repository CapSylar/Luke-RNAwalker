package walkerPack;

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

        System.out.println( "getSet preporcessing time " + group.getSetPreProcessingTime()/1000 + " microseconds");
        System.out.println( "getS" + group.getVectorPreProcessingTime()/1000 + " microseconds");

        long timeItTook = group.rankUsingSimilarity( new Sequence("CCCC") , new JaccardCoefficient() );

        System.out.println(group);
        System.out.println("It took " + timeItTook/1000 + " microseconds");

        group.filterSelection( new KNNOperator(20 ));

        timeItTook = group.rankUsingSimilarity( new Sequence("ACGCCUCCACGAGUGUCUU") , new SED() );

        System.out.println(group);
        System.out.println("It took " + timeItTook/1000 + " microseconds");
    }
}
