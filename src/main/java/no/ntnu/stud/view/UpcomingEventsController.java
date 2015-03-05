package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Kristian on 02/03/15.
 */
public class UpcomingEventsController {

    private MainApp mainApp;
    private Appointment appointment;

    @FXML
    private Button btnNewAppointment, btnEditAppointment;

    public UpcomingEventsController(){

    }

    @FXML
    private void initialize(){
        appointment = new Appointment("Title",LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1),1,"desc123","loc123",3, 10);

    }

    @FXML
    public void handleAddEvent(){
        mainApp.showAppointmentDialog(null);
    }

    @FXML
    public void handleEditEvent(){
        mainApp.showAppointmentDialog(appointment);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
