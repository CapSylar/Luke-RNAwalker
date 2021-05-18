package GUICode;

import javafx.beans.property.DoubleProperty;
import walkerPack.DiffCreator;
import walkerPack.GraphicalInterface;
import walkerPack.InternalApplicationException;
import walkerPack.Utilities;

public class DiffPaneState
{
    private String Seq1Path = "" ;
    private String Seq2Path = "" ;
    private String DiffSavePath = "" ;

    private DiffPaneView currentView;

    public DiffPaneState(DiffPaneView currentView)
    {
        this.currentView = currentView;
    }

    public String getSeq1Path()
    {
        return Seq1Path;
    }

    public void setSeq1Path(String seq1Path)
    {
        Seq1Path = seq1Path;
    }

    public String getSeq2Path()
    {
        return Seq2Path;
    }

    public void setSeq2Path(String seq2Path)
    {
        Seq2Path = seq2Path;
    }

    public String getDiffPath()
    {
        return DiffSavePath;
    }

    public void setDiffPath(String diffPath)
    {
        DiffSavePath = diffPath;
    }

    public void LoadFirstSeqButtonPressed()
    {
        currentView.setProgressBarProgress(0); // reset just in case
        String append = Utilities.BrowseForFile("Browse for Seq 1");

        if ( append != null )
        {
            this.currentView.setSequence1Field(append) ;
            this.Seq1Path = append;
        }
    }

    public void LoadSecondSeqButtonPressed()
    {
        currentView.setProgressBarProgress(0); // reset just in case
        String append = Utilities.BrowseForFile("Browse for Seq 2");

        if ( append != null )
        {
            this.currentView.setSequence2Field(append); ;
            this.Seq2Path = append;
        }
    }

    public void SaveDiffButtonPressed()
    {
        currentView.setProgressBarProgress(0); // reset just in case
        String append = Utilities.SaveFileLocation("Save Location for Diff Script");

        if ( append != null )
        {
            this.currentView.setSaveLocationTextField(append); ;
            this.DiffSavePath = append;
        }
    }


    public void Calculate(DoubleProperty progress)
    {
        // we write to progress to indicate our progress
        try
        {
            DiffCreator newCreator = new DiffCreator( Seq1Path , Seq2Path ); // does error logging itself

            //TODO: find a better way to do this
            // we check the save path before executing the diff calc because the apply routine updates the progress bar
            // and since the save may later fail because the path is invalid , the progress bar moves in this case
            // we don't want that!

            if ( DiffSavePath.isBlank() )
                throw new InternalApplicationException("Invalid Diff Save Path" );

            newCreator.BuildDiff(GraphicalInterface.currentManager.getUpdateCost(),GraphicalInterface.currentManager.getDeleteCost()
                    ,GraphicalInterface.currentManager.getInsertCost() , progress ); // pull costs from settings manager that has the update costs
            newCreator.SaveDiffScriptXML(DiffSavePath);
        }
        catch ( InternalApplicationException exp )
        {
            GraphicalInterface.logManager.logError(exp.getMessage() , 3000 );
        }
    }


}
