package GUICode;

import walkerPack.EditScript;
import walkerPack.GraphicalInterface;
import walkerPack.InternalApplicationException;
import walkerPack.Utilities;

public class SettingsPaneState
{
    private String DiffPath ;
    private String ReverseSavePath ;

    private int insertCost;
    private int deleteCost;
    private int updateCost;

    private SettingsPaneView currentView;

    public SettingsPaneState(SettingsPaneView currentView)
    {
        this.currentView = currentView;
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

}
