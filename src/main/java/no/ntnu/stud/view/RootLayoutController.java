package no.ntnu.stud.view;

import javafx.fxml.FXML;
import no.ntnu.stud.MainApp;
import org.controlsfx.dialog.Dialogs;


/**
 * Created by Kristian on 25/02/15.
 */
public class RootLayoutController {
    private MainApp mainApp;

    public RootLayoutController(){
        //trollllooolllll
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private void handleAbout() {
        Dialogs.create().title("CalendarApp").masthead("About").message("Author: Swag\nWebsite: https://github.com/sklirg/fellesprosjekt-2015").showInformation();
    }

    @FXML
    private void hanldeExit() {
        System.exit(1);
    }

}
