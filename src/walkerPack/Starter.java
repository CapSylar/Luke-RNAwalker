package walkerPack;

import java.io.SequenceInputStream;

public class Starter
{
    public static void main(String args[])
    {
        // load settings manager
//        SettingsManager manager = SettingsManager.load() ; // load settings into app
//
//        GraphicalInterface.currentManager = manager ; // bind manager
//        GraphicalInterface.main(args); // launch graphical interface

        int vec[] = {50,1,1,1,1,1,1,1,1};

        VectorSequence hello1 = new VectorSequence( "GAUCRMSVN" , 150 , vec , true );
        VectorSequence hello2 = new VectorSequence( "GAUCRMSVN" );

        System.out.println(hello1.getModule());
        System.out.println(hello1.getAveragedModule());

        System.out.println(hello1);

    }
}
