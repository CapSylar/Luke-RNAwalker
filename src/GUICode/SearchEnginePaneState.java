package GUICode;
import OperationsWrappers.GraphicalConstructor;
import walkerPack.*;

import java.text.DecimalFormat;

public class SearchEnginePaneState implements CallablePaneState
{
    private SearchEnginePaneView currentView;
    private SearchGroupOperation selectedOperations[] ;
    private String query = "" ;

    private SearchSnapShotHolder lastSnapshots = null ;

    public SearchEnginePaneState(SearchEnginePaneView currentView)
    {
        this.currentView = currentView;
        selectedOperations = new SearchGroupOperation[5]; // 5 menu-buttons

        // init text area
        this.currentView.printToTextArea("No Results To Display...");

        // call view to setup menubuttons and set callbacks
        this.currentView.InitAllMenuButtons( this );
    }

    public void menuButtonPressed( int slotIndex )
    {
        // buttons are used to show the state of filtering at different times, display snapshots
        if ( this.lastSnapshots == null )
            return;
        this.currentView.printToTextArea(this.lastSnapshots.getSnapshot(slotIndex).toString());

        // tell the view to update the time text
        if ( this.selectedOperations[slotIndex] != null )
        {
            DecimalFormat format = new DecimalFormat("##.###");
            this.currentView.setOpTimeText(format.format(lastSnapshots.getSnapshot(slotIndex).getTime() / 1000000.0) + " ms");
        }
        else
            this.currentView.setOpTimeText("0 ms");
    }

    public void setMenuState( int operationIndex , int slotIndex ) // called when the user selects an operation in a Slot
    {
        // first all traces remove previous search
        this.lastSnapshots = null;
        this.currentView.setTotalTimeText("None");
        this.currentView.setOpTimeText("None");
        this.currentView.printToTextArea("No Results To Display...");

        // load new operation next

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
    @Override
    public void setOperationInSlot( SearchGroupOperation Operation , int slotIndex , String message )
    {
        // message is to be displayed on the MenuButton, this makes it possible for a custom Message
        this.currentView.changeMenuButtonSelection(slotIndex, message);
        this.selectedOperations[slotIndex] = Operation;
    }


    public void SearchPressed ()
    {
        // do the search with the selected operations in the stack

        boolean present = false;
        for ( int i = 0 ; i < selectedOperations.length ; ++i )
        {
            if ( selectedOperations[i] != null )
            {
                present = true;
                break;
            }
        }

        if ( !present ) // all operations are null
            GraphicalInterface.logManager.logError("Specify At Least One Operation" , 2000 );
        else if ( this.query.isBlank() )
            GraphicalInterface.logManager.logError("Query is Blank" , 2000 );
        else if ( selectedOperations[0] == null )
            GraphicalInterface.logManager.logError("First Slot can't be empty" , 2000 );

        if (!present || this.query.isBlank() || selectedOperations[0] == null )
            return;

        // Create a snapshot holder, give it the stack and then print the latest snapshot on the screen for the user
        //TODO: xml loaded every time, peak of stupidity
        SearchGroup group = SearchGroup.fromXML("test-files/FormattedSequences.xml" ,
                GraphicalInterface.currentManager.getTf(), GraphicalInterface.currentManager.getIdf() );
        this.lastSnapshots = new SearchSnapShotHolder(group);

        long totalTime = lastSnapshots.applyOperationStack(new Sequence(query) , this.selectedOperations );

        this.currentView.printToTextArea(lastSnapshots.getSnapshot(selectedOperations.length-1).toString());

        // tell the view to update itself

        // get the total time it took

        DecimalFormat format = new DecimalFormat("##.###");
        this.currentView.setOpTimeText(format.format(lastSnapshots.getSnapshot(0).getTime()/1000000.0) + " ms");
        this.currentView.setTotalTimeText(format.format(totalTime/1000000.0) + " ms");
        GraphicalInterface.logManager.logMessage("Success!" , 2000 );
    }

    public void setQueryField( String query )
    {
        // TODO: no checking is done if Sequence is valid
        this.query = query;
    }

}
