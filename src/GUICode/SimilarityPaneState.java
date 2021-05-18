package GUICode;

import OperationsWrappers.GraphicalConstructor;
import SearchGroupOperations.TimeNSimilarity;
import walkerPack.*;
import java.text.DecimalFormat;

public class SimilarityPaneState implements CallablePaneState
{
    private SimilarityPaneView currentView;
    private SimilarityMeasure currentMeasure = null;

    private String SequenceOne ;
    private String SequenceTwo ;

    public SimilarityPaneState(SimilarityPaneView currentView)
    {
        this.currentView = currentView;

        // init the view
        this.currentView.InitMenuButton(this);
    }

    public void menuButtonPressed( int operationIndex )
    {
        String OperationClassName = WhatTheHellIsThis.OperationName[operationIndex][0]; // get Class Name from LUT

        try
        {
            GraphicalConstructor constructor = (GraphicalConstructor) Class.forName(OperationClassName).getDeclaredConstructor().newInstance();
            // constructor can't directly construct the object, so we have to give him a way to call the method back again later
            constructor.Construct( this , 0 ); // no slots in this case
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setOperationInSlot(SearchGroupOperation Operation, int slotIndex, String message)
    {
        this.currentMeasure = ( SimilarityMeasure ) Operation;
        this.currentView.setMenuButtonText(message);
    }

    public void compareButtonPressed()
    {
        if ( currentMeasure == null )
        {
            GraphicalInterface.logManager.logError("Operation is  unspecified" , 2000 );
            return;
        }

        if ( this.SequenceOne == null || this.SequenceTwo == null)
        {
            GraphicalInterface.logManager.logError("Sequence field empty" , 2000 );
            return;
        }

        Sequence one = new Sequence(this.SequenceOne);
        Sequence two = new Sequence(this.SequenceTwo);

        SequenceBlock superOne = new SequenceBlock(one);
        SequenceBlock superTwo = new SequenceBlock(two);
        superOne.process();
        superTwo.process();

        TimeNSimilarity returned = this.currentMeasure.calculateSimilarity( superOne , superTwo );

        long PreprocessTimeOne = 0 , PreprocessTimeTwo = 0;
        //TODO: replace the line below
        switch (this.currentMeasure.getType())
        {
            case SED:
                break;
            case Set:
                PreprocessTimeOne =  superOne.getSetPreProcessingTime();
                PreprocessTimeTwo =  superTwo.getSetPreProcessingTime();
                break;
            case Vector:
                PreprocessTimeOne = superOne.getVectorPreProcessingTime();
                PreprocessTimeTwo = superTwo.getVectorPreProcessingTime();
                break;
        }

        DecimalFormat currentFormat = new DecimalFormat("##.####");
        this.currentView.setSequenceOneProcessingField(currentFormat.format(PreprocessTimeOne/1000000.0) +" ms");
        this.currentView.setSequenceTwoProcessingField(currentFormat.format(PreprocessTimeTwo/1000000.0) + " ms");
        this.currentView.setTotalTimeField(currentFormat.format(returned.time/1000000.0) + " ms");
        this.currentView.setSimilarityText(currentFormat.format(returned.similarity));

        GraphicalInterface.logManager.logMessage("Success!" , 2000 );
    }

    public void setSequenceOne( String s )
    {
        this.SequenceOne = s;
    }

    public void setSequenceTwo( String s )
    {
        this.SequenceTwo = s;
    }
}
