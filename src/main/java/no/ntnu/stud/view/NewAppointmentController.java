package no.ntnu.stud.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Room;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Kristian on 03/03/15.
 */
public class NewAppointmentController {
    private MainApp mainApp;
    private Stage newAppointmentStage;
    private Appointment appointment;
    private boolean okClicked = false;
    @FXML
    private TextField inpEmail;
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

    public boolean isOkClicked() {
        return okClicked;
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
        //ArrayList<Room> rooms = gd.getAllAvailableRooms(startTime, endTime, date);
        ObservableList<Room> rooms = gd.getAllAvailableRooms(startTime, endTime, date);
        btnRoom.setItems(rooms);
        /*for(Room r:rooms){
            String str = "Room: "+r.getName()+"|Capacity: "+r.getCapacity()+"\n";
            btnRoom.getItems().add(str);
            System.out.println("Room: "+r.getName()+"|Capacity: "+r.getCapacity());
        }*/
    }
    @FXML
    void updateTest(){
        inpRoom.setText((String) btnRoom.getValue());
    }
    @FXML
    private void voidHandleSave() {

    }

    @FXML
    private void handleClose() {
        mainApp.showCalendarView();
    }
}