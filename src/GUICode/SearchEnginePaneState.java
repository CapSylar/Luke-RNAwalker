package GUICode;
import OperationsWrappers.GraphicalConstructor;
import walkerPack.SearchGroup;
import walkerPack.SearchGroupOperation;
import walkerPack.SearchSnapShotHolder;
import walkerPack.Sequence;

public class SearchEnginePaneState
{
    private SearchEnginePaneView currentView;
    private SearchGroupOperation selectedOperations[] ;
    private String query = "" ;

    private SearchSnapShotHolder lastSnapshots = null ;

    public SearchEnginePaneState(SearchEnginePaneView currentView)
    {
        this.currentView = currentView;
        selectedOperations = new SearchGroupOperation[5]; // 5 menu-buttons

        // call view to setup menubuttons and set callbacks

        this.currentView.InitAllMenuButtons( this );
    }

    public void menuButtonPressed( int slotIndex )
    {
        // buttons are used to show the state of filtering at different times, display snapshots
        if ( this.lastSnapshots == null )
            return;     //TODO: warn the user

        this.currentView.printToTextArea(this.lastSnapshots.getSnapshot(slotIndex).toString());
    }

    public void setMenuState( int operationIndex , int slotIndex ) // called when the user selects an operation in a Slot
    {
        String OperationClassName = WhatTheHellIsThis.OperationName[operationIndex][0]; // get Class Name from LUT

        try
        {
            GraphicalConstructor constructor = (GraphicalConstructor) Class.forName(OperationClassName).getDeclaredConstructor().newInstance();
            // constructor can't directly construct the object, so we have to give him a way to call the method back again later
            constructor.Construct( this , slotIndex );

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // called by GraphicalConstructor that wrap around the SearchGroupOperations
    public void setOperationInSlot( SearchGroupOperation Operation , int slotIndex , String message )
    {
        // message is to be displayed on the MenuButton, this makes it possible for a custom Message
        this.currentView.changeMenuButtonSelection(slotIndex, message);
        this.selectedOperations[slotIndex] = Operation;
    }


    public void SearchPressed ()
    {
        // do the search with the selected operations in the stack
        //TODO: first check if any slot has a valid operation, if not warn the user

        boolean present = false;
        for ( int i = 0 ; i < selectedOperations.length ; ++i )
        {
            if ( selectedOperations[i] != null )
            {
                present = true;
                break;
            }
        }

        if (!present || this.query.isBlank() || selectedOperations[0] == null ) // first slot should not be null
        {
            // TODO: warm the user
            System.out.println("WARNING AN ERROR OCCURED");
            return;
        }

        // Create a snapshot holder, give it the stack and then print the latest snapshot on the screen for the user
        //TODO: xml loaded every time, peak of stupidity
        SearchGroup group = SearchGroup.fromXML("test-files/FormattedSequences.xml" , true , true );
        SearchSnapShotHolder holder = new SearchSnapShotHolder(group);
        holder.applyOperationStack(new Sequence(query) , this.selectedOperations );

        this.currentView.printToTextArea(holder.getSnapshot(selectedOperations.length-1).toString());
        this.lastSnapshots = holder ; // save it for later use, if the user wants to view states himself
    }

    public void setQueryField( String query )
    {
        // TODO: no checking is done if Sequence is valid
        this.query = query;
    }


}
