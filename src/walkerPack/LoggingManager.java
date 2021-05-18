package walkerPack;

import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoggingManager
{
    private JFXSnackbar snackbar;

    private Pane snackBarPanePositive ;
    private Pane snackBarPaneNegative ;
    private Text PositiveText;
    private Text NegativeText;

    public LoggingManager( Pane rootPane , Pane snackBarPanePositive , Pane snackBarPaneNegative )
    {
        this.snackbar = new JFXSnackbar( rootPane );
        this.snackBarPanePositive = snackBarPanePositive ;
        this.snackBarPaneNegative = snackBarPaneNegative ;

        this.PositiveText = ( Text ) (( Pane ) snackBarPanePositive ).getChildren().get(0);
        this.NegativeText = ( Text ) (( Pane ) snackBarPaneNegative ).getChildren().get(0);
    }

    public void logError( String message , double duration_ms )
    {
        this.NegativeText.setText(Utilities.TruncateIfLonger(message,35));
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent( this.snackBarPaneNegative  , new Duration( duration_ms )));
    }

    public void logMessage ( String message , double duration_ms )
    {
        this.PositiveText.setText(Utilities.TruncateIfLonger(message,35));
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent( this.snackBarPanePositive  , new Duration( duration_ms )));
    }
}
