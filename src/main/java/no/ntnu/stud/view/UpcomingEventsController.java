package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import no.ntnu.stud.MainApp;

/**
 * Created by Kristian on 02/03/15.
 */
public class UpcomingEventsController {

    private MainApp mainApp;

    @FXML
    private Button btnNewAppointment, btnEditAppointment;

    public UpcomingEventsController(){

    }

    @FXML
    public void handleAddEvent(){

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNewAppointment() {
        mainApp.showAppointmentDialog(null);
    }
}
