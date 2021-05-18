package GUICode;

import walkerPack.*;

public class SettingsPaneState
{
    private String DiffPath ;
    private String ReverseSavePath ;

    private int insertCost;
    private int deleteCost;
    private int updateCost;

    private boolean TF;
    private boolean IDF;

    private SettingsPaneView currentView;

    public SettingsPaneState(SettingsPaneView currentView)
    {
        this.currentView = currentView;

        this.insertCost = GraphicalInterface.currentManager.getInsertCost();
        this.deleteCost = GraphicalInterface.currentManager.getDeleteCost();
        this.updateCost = GraphicalInterface.currentManager.getUpdateCost();

        this.TF = GraphicalInterface.currentManager.getTf();
        this.IDF = GraphicalInterface.currentManager.getIdf();

        // Settings pane, gets text field contents from the SettingsManager
        currentView.setInsertCostField(""+ this.insertCost);
        currentView.setDeleteCostField(""+ this.deleteCost);
        currentView.setUpdateCostField(""+ this.updateCost);
        currentView.setTFBoxState(this.TF);
        currentView.setIDFBoxState(this.IDF);
    }

    public void setInsertCost(String insertCost)
    {
        try
        {
            this.insertCost = Integer.parseInt(insertCost);
            GraphicalInterface.currentManager.setInsertCost(this.insertCost);
        }
        catch ( NumberFormatException exc )
        {
            ; // do nothing
        }
    }

    public void setDeleteCost(String deleteCost)
    {
        try
        {
            this.deleteCost = Integer.parseInt(deleteCost);
            GraphicalInterface.currentManager.setDeleteCost(this.deleteCost);
        }
        catch ( NumberFormatException exc )
        {
            ; // do nothing
        }
    }

    public void setUpdateCost(String updateCost)
    {
        try
        {
            this.updateCost = Integer.parseInt(updateCost);
            GraphicalInterface.currentManager.setUpdateCost(this.updateCost);
        }
        catch ( NumberFormatException exc )
        {
            ; // do nothing
        }
    }

    public void loadDiffButtonPressed()
    {
        String append = Utilities.BrowseForFile("Browse for Diff");

        if ( append != null )
        {
            this.currentView.setDiffField(append);
            DiffPath = append;
        }
    }

    public void saveReverseButtonPressed()
    {
        String append = Utilities.SaveFileLocation("Save Reverse Diff");

        if ( append != null )
        {
            this.currentView.setSaveReverseField(append);
            ReverseSavePath = append;
        }
    }

    public void reverse()
    {
        // TODO: relocate this code chunk elsewhere

        try
        {
            EditScript Reversed = EditScript.fromXMLFile(DiffPath);
            Reversed.getReverse().toXMLFile(ReverseSavePath);// save to path
        }
        catch ( InternalApplicationException excp )
        {
            // log error specified
            GraphicalInterface.logManager.logError(excp.getMessage() , 3000 );
        }
    }

    public void enableTFPressed( boolean state )
    {
        this.TF = state;

        if ( !this.IDF && !this.TF )
            this.currentView.setIDFBoxState(true);

        // update the settings manager
        GraphicalInterface.currentManager.setTf(this.TF);
    }

    public void enableIDFPressed( boolean state )
    {
        this.IDF = state;
        // user shouldn't be able to uncheck both TF and IDF, we need at least one

        if ( !this.TF && !this.IDF )
            this.currentView.setTFBoxState(true); // in this case re-set TF check box

        GraphicalInterface.currentManager.setIdf(this.IDF);
    }

}
