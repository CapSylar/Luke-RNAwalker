package GUICode;

import SearchGroupOperations.SetSimilarityMethod;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class SimilarityPaneView
{
    private MenuButton MenuButton;
    private JFXTextField SequenceOneField;
    private JFXTextField SequenceTwoField;
    private Text SequenceOneProcessingField;
    private Text SequenceTwoProcessingField;
    private Text TotalTimeField;
    private Text SimilarityText;

    public SimilarityPaneView(javafx.scene.control.MenuButton menuButton, JFXTextField sequenceOneField,
                              JFXTextField sequenceTwoField, Text sequenceOneProcessingField,
                              Text sequenceTwoProcessingField, Text totalTimeField , Text similarityText)
    {
        MenuButton = menuButton;
        SequenceOneField = sequenceOneField;
        SequenceTwoField = sequenceTwoField;
        SequenceOneProcessingField = sequenceOneProcessingField;
        SequenceTwoProcessingField = sequenceTwoProcessingField;
        SimilarityText = similarityText;
        TotalTimeField = totalTimeField;
    }

    public void InitMenuButton( SimilarityPaneState state )
    {
        for ( int i = 0 ; i < 5 ; ++i ) // get first 5 measures from the list
        {
            int finalI = i;
            MenuItem newItem = new MenuItem( WhatTheHellIsThis.OperationName[i][1] );
            newItem.setOnAction(actionEvent -> {state.menuButtonPressed(finalI);});
            MenuButton.getItems().add(newItem);
        }
    }

    public void setMenuButtonText( String message )
    {
        this.MenuButton.setText(message);
    }

    public void setSequenceOneProcessingField(String sequenceOneProcessingField)
    {
        SequenceOneProcessingField.setText(sequenceOneProcessingField);
    }

    public void setSequenceTwoProcessingField(String sequenceTwoProcessingField)
    {
        SequenceTwoProcessingField.setText(sequenceTwoProcessingField);
    }

    public void setTotalTimeField(String totalTimeField)
    {
        TotalTimeField.setText(totalTimeField);
    }

    public void setSimilarityText(String similarity)
    {
        SimilarityText.setText(similarity);
    }
}
