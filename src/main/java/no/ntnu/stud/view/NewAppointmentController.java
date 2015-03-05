package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.model.Appointment;

/**
 * Created by Kristian on 03/03/15.
 */
public class NewAppointmentController {
    private MainApp mainApp;
    private Stage newAppointmentStage;
    private Appointment appointment;
    private boolean okClicked = false;
    @FXML
<<<<<<< HEAD
    private Label title;
    @FXML
    private TextField inpEmail;
=======
    private TextField inpTitle;
>>>>>>> f5f72e5c7b918e57e156d8b61b693a4022cced4f
    @FXML
    private DatePicker inpDate;
    @FXML
    private TextField inpFrom, inpTo;
    @FXML
    private TextField inpMaxAttend;
    @FXML
    private TextField inpRoom;
    @FXML
    private TextField inpUser;
    @FXML
    private TextArea inpDesc;
    @FXML
    private Button btnSave, btnClose;


    public NewAppointmentController() {

    }

    @FXML
    private void initialize() {
    }

    public void setNewAppointmentStage(Stage newAppointmentStage) {
        this.newAppointmentStage = newAppointmentStage;
    }
    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp;}

    public void insertAppointmentData(Appointment appointment){
        title.setText("");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void voidHandleSave() {

    }

    @FXML
    private void handleClose() {
        mainApp.showCalendarView();
    }
}