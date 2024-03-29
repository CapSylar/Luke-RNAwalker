package GUICode;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SearchEnginePaneView {

    private TextArea SearchEnginePaneResultsField;

    private SplitMenuButton SearchEnginePaneMenuButton1;
    private SplitMenuButton SearchEnginePaneMenuButton2;
    private SplitMenuButton SearchEnginePaneMenuButton3;
    private SplitMenuButton SearchEnginePaneMenuButton4;
    private SplitMenuButton SearchEnginePaneMenuButton5;
    private JFXTextField SearchEnginePaneQueryField;

    private Text SetPreprocessingText;
    private Text VectorPreprocessingText;

    private Text OpTimeText;
    private javafx.scene.text.Text TotalTimeText;

    private ArrayList<SplitMenuButton> MenuButtons = new ArrayList<>() ;

    public SearchEnginePaneView(TextArea searchEnginePaneResultsField, SplitMenuButton searchEnginePaneMenuButton1,
                                SplitMenuButton searchEnginePaneMenuButton2 , SplitMenuButton searchEnginePaneMenuButton3,
                                SplitMenuButton searchEnginePaneMenuButton4 , SplitMenuButton searchEnginePaneMenuButton5,
                                JFXTextField searchEnginePaneQueryField , Text SearchEnginePaneOpTimeText ,
                                Text SearchEnginePaneTotalTimeText , Text SearchEnginePaneSetPreprocessingText ,
                                Text SearchEnginePaneVectorPreprocessingText )
    {
        SearchEnginePaneResultsField = searchEnginePaneResultsField;
        SearchEnginePaneMenuButton1 = searchEnginePaneMenuButton1;
        SearchEnginePaneMenuButton2 = searchEnginePaneMenuButton2;
        SearchEnginePaneMenuButton3 = searchEnginePaneMenuButton3;
        SearchEnginePaneMenuButton4 = searchEnginePaneMenuButton4;
        SearchEnginePaneMenuButton5 = searchEnginePaneMenuButton5;

        OpTimeText = SearchEnginePaneOpTimeText;
        TotalTimeText = SearchEnginePaneTotalTimeText;

        SearchEnginePaneQueryField = searchEnginePaneQueryField;

        SetPreprocessingText = SearchEnginePaneSetPreprocessingText ;
        VectorPreprocessingText = SearchEnginePaneVectorPreprocessingText ;

        MenuButtons.add(SearchEnginePaneMenuButton1);
        MenuButtons.add(SearchEnginePaneMenuButton2);
        MenuButtons.add(SearchEnginePaneMenuButton3);
        MenuButtons.add(SearchEnginePaneMenuButton4);
        MenuButtons.add(SearchEnginePaneMenuButton5);
    }

    public void InitAllMenuButtons( SearchEnginePaneState state )
    {
        // state is passed so we can hook elements listeners that call it when something changes

        for ( int i = 0 ; i < MenuButtons.size() ; ++i )
        {
            int finalI = i;
            MenuButtons.get(i).setOnAction(actionEvent -> state.menuButtonPressed(finalI));
        }

        for ( int i = 0; i < MenuButtons.size() ; ++i )
        {
            InitMenuButton( MenuButtons.get(i)  , state , i );
        }
    }

    private void InitMenuButton( SplitMenuButton button , SearchEnginePaneState state , int buttonIndex )
    {
        for ( int i = 0 ; i < WhatTheHellIsThis.OperationName.length ; ++i ) // 4 operations only supported for now
        {
            MenuItem newItem = new MenuItem( WhatTheHellIsThis.OperationName[i][1] );
            int finalI = i;
            newItem.setOnAction(actionEvent -> state.setMenuState( finalI , buttonIndex));
            button.getItems().add(newItem);
        }
    }

    public void printToTextArea(String text)
    {
        this.SearchEnginePaneResultsField.setText(text);
    }

    public void changeMenuButtonSelection ( int menuButtonIndex , String message )
    {
        this.MenuButtons.get(menuButtonIndex).setText(message);
    }

    public void setOpTimeText(String opTimeText)
    {
        OpTimeText.setText(opTimeText);
    }

    public void setTotalTimeText(String totalTimeText)
    {
        TotalTimeText.setText(totalTimeText);
    }

    public void setSetPreprocessingText(String setPreprocessingText)
    {
        SetPreprocessingText.setText(setPreprocessingText);
    }

    public void setVectorPreprocessingText(String vectorPreprocessingText)
    {
        VectorPreprocessingText.setText(vectorPreprocessingText);
    }
}