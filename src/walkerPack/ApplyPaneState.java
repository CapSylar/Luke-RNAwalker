package walkerPack;

class ApplyPaneState
{
   static String Seq ;
   static String Diff ;
   static String ResultingSeq;

    public static String getSeq()
    {
        return Seq;
    }

    public static void setSeq(String seq)
    {
        Seq = seq;
    }

    public static String getDiff()
    {
        return Diff;
    }

    public static void setDiff(String diff)
    {
        Diff = diff;
    }

    public static String getResultingSeq()
    {
        return ResultingSeq;
    }

    public static void setResultingSeq(String resultingSeq)
    {
        ResultingSeq = resultingSeq;
    }

    public static void applyDiff ()
    {
        DiffApplicator applicator = new DiffApplicator(ApplyPaneState.Seq) ;
        applicator.applyDiff(Diff); // apply diff on sequence
        applicator.SavePatchedSequence(ResultingSeq); // save it at location specified
    }
}
