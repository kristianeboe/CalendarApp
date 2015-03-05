package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by Kristian on 03/03/15.
 */
public class NewAppointmentController {
    private MainApp mainApp;
    private Stage newAppointmentStage;
    private Appointment appointment;
    private boolean okClicked = false;
    @FXML
    private Label header;
    @FXML
    private TextField inpTitle;
    @FXML
    private DatePicker inpDate;
    @FXML
    private TextField inpFrom, inpTo;
    @FXML
    private TextField inpMaxAttend;
    @FXML
    private TextField inpRoom;
    @FXML
    private TextField inpInvite;
    @FXML
    private TextArea inpDesc;
    @FXML
    private ComboBox btnRoom;
    @FXML
    private Button btnSave, btnClose, btnAddUser;


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
        /*if (appointment.getTitle() != null) {
            header.setText(appointment.getTitle());
            inpDate.setValue(appointment.getDate());
            inpFrom.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpTo.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpMaxAttend.setText(Integer.toString(appointment.getAttending()));
            //setRoom field
            inpDesc.setText(appointment.getDescription());
        } else {*/
            inpDate.setValue(appointment.getDate());
            inpFrom.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpTo.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpMaxAttend.setText(Integer.toString(appointment.getAttending()));
        //}
    }

    public void addAppointment() {
        String title = inpTitle.getText();
        LocalDate date = inpDate.getValue();
        LocalTime startTime = LocalTime.parse(inpFrom.getText());
        LocalTime endTime = LocalTime.parse(inpTo.getText());
        int maxAttending = Integer.parseInt(inpMaxAttend.getText());

        // If roomID, use roomID. If not, use location.
        int roomID = 0;
        String location = "";

        String description = inpDesc.getText();
        int ownerID = 0;

        /*
        for (User u : invited) {}
         */

        Appointment appointment = new Appointment(title, date, startTime, endTime, ownerID, description, location, roomID, maxAttending);
    }

    @FXML
    void getAllAvailableRooms(){
        //Temporary validation START
        inpDate.setStyle("-fx-border-width: 0px");
        inpFrom.setStyle("-fx-border-width: 0px");
        inpTo.setStyle("-fx-border-width: 0px");

        if(inpDate.getValue() == null){
            inpDate.setStyle("-fx-border-color: red" + "; -fx-border-width: 1px;");
            return;
        }else if(inpFrom.getText().isEmpty()){
            inpFrom.setStyle("-fx-border-color: red" + "; -fx-border-width: 1px;");
            return;
        }else if(inpTo.getText().isEmpty()){
            inpTo.setStyle("-fx-border-color: red" + "; -fx-border-width: 1px;");
            return;
        }
        //Temporary validation END

        btnRoom.getItems().clear();
        GetData gd = new GetData();
        LocalTime startTime = LocalTime.parse(inpFrom.getText());
        LocalTime endTime = LocalTime.parse(inpTo.getText());
        LocalDate date = inpDate.getValue();
        ArrayList<Room> rooms = gd.getAllAvailableRooms(startTime, endTime, date, Integer.parseInt(inpMaxAttend.getText()));
        for(Room r:rooms){
            String str = "Room: "+r.getName()+"|Capacity: "+r.getCapacity()+"\n";
            btnRoom.getItems().add(str);
            System.out.println("Room: "+r.getName()+"|Capacity: "+r.getCapacity());
        }
    }
    @FXML
    private void voidHandleSave() {

    }

    @FXML
    private void handleClose() {
        mainApp.showCalendarView();
    }
}