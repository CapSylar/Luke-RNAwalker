package walkerPack;

import javafx.beans.property.DoubleProperty;

class ApplyPaneState
{
   private String Seq = "" ;
   private String Diff = "" ;
   private String ResultingSeq = "" ;

   private ApplyPaneView currentView;

    public ApplyPaneState(ApplyPaneView currentView)
    {
        this.currentView = currentView;
    }

    public String getSeq()
    {
        return Seq;
    }

    public void setSeq(String seq)
    {
        Seq = seq;
    }

    public String getDiff()
    {
        return Diff;
    }

    public void setDiff(String diff)
    {
        Diff = diff;
    }

    public String getResultingSeq()
    {
        return ResultingSeq;
    }

    public void setResultingSeq(String resultingSeq)
    {
        ResultingSeq = resultingSeq;
    }

    public void loadSeqButtonPressed()
    {
        this.currentView.setProgressBar(0); // reset just in case
        String append = Utilities.BrowseForFile("Browse for Seq");

        if ( append != null )
        {
            this.currentView.setSeqField(append);
            Seq = append;
        }
    }

    public void loadDiffButtonPressed()
    {
        this.currentView.setProgressBar(0); // reset just in case
        String append = Utilities.BrowseForFile("Browse for Diff Script");

        if ( append != null )
        {
            this.currentView.setDiffField(append);
            Diff = append;
        }
    }

    public void saveButtonPressed()
    {
        this.currentView.setProgressBar(0); // reset just in case
        String append = Utilities.SaveFileLocation("Save Location for Patched Sequence");

        if ( append != null )
        {
            this.currentView.setResultField(append);
            this.ResultingSeq = append;
        }
    }


    public void applyDiff (DoubleProperty Progress)
    {
        try
        {
            DiffApplicator applicator = new DiffApplicator(Seq) ; // does logging and checking itself

            //TODO: find a better way to do this
            // we check the save path before executing the apply because the apply routine updates the progress bar
            // and since the save may later fail because the path is invalid , the progress bar moves in this case
            // we don't want that!

            if ( ResultingSeq.isBlank() ) // invalid path
                throw new InternalApplicationException("Invalid Save Path Specified!"); // throw before trying to compute

            applicator.applyDiff(Diff , Progress ); // apply diff on sequence
            applicator.SavePatchedSequence(ResultingSeq); // save it at location specified
        }
        catch ( InternalApplicationException excp )
        {
            GraphicalInterface.logManager.logError(excp.getMessage() , 3000 );
        }

    }
}
