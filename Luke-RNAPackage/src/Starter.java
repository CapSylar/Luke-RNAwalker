public class Starter {

    public static void main(String args[]) throws Exception
    {
        DiffCreator newCreator = new DiffCreator( "testseq.xml" , "testseq2.xml" ) ;
        newCreator.BuildDiff(1,1,1);
        newCreator.SaveDiffScriptXML("ResultEditScript.xml");
    }
}



