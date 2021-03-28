package walkerPack;

import java.io.*;

public class SettingsManager implements Serializable
{
    private static final long serialVersionUID = 6529685098267757690L;

    private int insertCost = 0 ;
    private int deleteCost = 0 ;
    private int updateCost = 0 ;

    public SettingsManager(int insertCost, int deleteCost, int updateCost)
    {
        this.insertCost = insertCost;
        this.deleteCost = deleteCost;
        this.updateCost = updateCost;
    }

    public int getInsertCost()
    {
        return insertCost;
    }

    public void setInsertCost(String InsertCost)
    {
        try
        {
            insertCost = Integer.parseInt(InsertCost);
        }
        catch ( NumberFormatException exc )
        {
            ; // do nothing
        }
//        PrintState();

    }

    public int getDeleteCost()
    {
        return deleteCost;
    }

    public void setDeleteCost(String DeleteCost)
    {
        try
        {
            deleteCost = Integer.parseInt(DeleteCost);
        }
        catch ( NumberFormatException exc )
        {
            ; // do nothing
        }
//        PrintState();

    }

    public int getUpdateCost()
    {
        return updateCost;
    }

    public void setUpdateCost(String UpdateCost)
    {
        try
        {
            updateCost = Integer.parseInt(UpdateCost);
        }
        catch ( NumberFormatException exc )
        {
            ; // do nothing
        }
//        PrintState();
    }

    public void PrintState()
    {
        System.out.println("UpdateCost: " + updateCost + "  DeleteCost: " + deleteCost + "  InsertCost: " + insertCost);
    }

    public void save()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("settings.txt") ;
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);

            oos.close();
            fos.close();
        }
        catch ( Exception exc )
        {
            exc.printStackTrace();
        }
    }

    public static SettingsManager load()
    {
        try
        {
            FileInputStream fis = new FileInputStream("settings.txt") ;
            ObjectInputStream ois = new ObjectInputStream(fis);

            SettingsManager toReturn = (SettingsManager) ois.readObject();

            ois.close();
            fis.close();

            return toReturn ;
        }
        catch ( Exception exc )
        {
            // failed for some reason
            return  new SettingsManager( 1 , 1 , 1) ;
        }
    }
}
