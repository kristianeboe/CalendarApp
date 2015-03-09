package no.ntnu.stud.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Group;
import no.ntnu.stud.model.Room;
import no.ntnu.stud.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private ComboBox<Room> btnRoom;
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

    public void insertAppointmentData(Appointment appointment){
            inpTitle.setText(appointment.getTitle());
            inpDesc.setText(appointment.getDescription());
            inpDate.setValue(appointment.getDate());
            inpFrom.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpTo.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpMaxAttend.setText(Integer.toString(appointment.getAttending()));
        //}
    }

    public Appointment addAppointment() {
        String title = inpTitle.getText();
        LocalDate date = inpDate.getValue();
        LocalTime startTime = LocalTime.parse(inpFrom.getText());
        LocalTime endTime = LocalTime.parse(inpTo.getText());
        int maxAttending = Integer.parseInt(inpMaxAttend.getText());
        int roomID;
        User owner;

        // If roomID, use roomID. If not, use location.

        // Cleanest hack ever to find roomID
        String roomValue = btnRoom.getValue().toString();
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher m = pattern.matcher(roomValue);
        if (m.find()) {
            roomID = Integer.parseInt(m.group(1));
            // System.out.println("match: " + m.group(1));
        } else {
            throw new IllegalArgumentException("FUCK YOU DIDNT FIND THE ROOM SHIT");
        }
        String location = "";

        owner = mainApp.getUser();

        String description = inpDesc.getText();

        /*
        for (User u : invited) {}
         */

        Appointment appointment = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, maxAttending);
        return appointment;
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
            btnRoom.getItems().add(r);
        }
    }
    @FXML
    private void voidHandleSave() {
        Appointment app = addAppointment();
        boolean DEBUG = false;

        if (DEBUG) {
            System.out.println("=== Created appointment ===");
            System.out.println("toStr: " + app);
            System.out.println("title: " + app.getTitle());
            System.out.println("desc : " + app.getDescription());
            System.out.println("date : " + app.getDate());
            System.out.println("start: " + app.getStart());
            System.out.println("end  : " + app.getEnd());
        }
        InsertData.createAppointment(app);

        if (DEBUG) {
            System.out.println("=== Let's try to get it back ===");
            Appointment app_check = GetData.getAppointment(app.getRoomID(), app.getDate(), app.getStart(), app.getEnd());
            System.out.println("toStr: " + app_check);
            System.out.println("id   : " + app_check.getAppointmentID());
            System.out.println("title: " + app_check.getTitle());
            System.out.println("desc : " + app_check.getDescription());
            System.out.println("date : " + app_check.getDate());
            System.out.println("start: " + app_check.getStart());
            System.out.println("end  : " + app_check.getEnd());
        }
    }

    @FXML
    private void searchForUser(){
        GetData gd = new GetData();
        String partOfName = inpInvite.getText();
        ArrayList<User> users;
        ArrayList<Group> groups;
        if(partOfName.length()>2){
            groups = gd.searchGroup(partOfName);
            users = gd.searchUser(partOfName);
            String results ="";
            if(users.size()>0){
                for(User usr:users){
                    results += usr.getFullName()+"\n";
                }
            }
            if(groups.size()>0){
                for(Group grp:groups){
                    results += grp.getName() +"\n";
                }
            }
            inpDesc.setText(results);
        }else{
            inpDesc.setText("");
        }
    }

    @FXML
    private void handleClose() {
        mainApp.showCalendarView();
    }
}