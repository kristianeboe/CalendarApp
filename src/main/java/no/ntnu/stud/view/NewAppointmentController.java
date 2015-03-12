package no.ntnu.stud.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.EditData;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Group;
import no.ntnu.stud.model.Room;
import no.ntnu.stud.model.User;

import java.time.LocalDate;
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
    private TextField inpInvite;
    @FXML
    private TextArea inpDesc, outInvited;
    @FXML
    private ComboBox<Room> btnRoom;
    @FXML
    private ComboBox<String> cmbSearchResults;
    @FXML
    private Button btnSave, btnClose, btnAddUser;
    @FXML
    private TextField inpLocation;
    @FXML
    private RadioButton radioWork, radioPersonal;
    @FXML
    private ToggleGroup radioGroup;
    @FXML
    ListView invitedUsersList;

    public NewAppointmentController() {

    }

    @FXML
    private void initialize() {
        activateFocusedProperties();
    }

    public void setNewAppointmentStage(Stage newAppointmentStage) {
        this.newAppointmentStage = newAppointmentStage;
    }
    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp;}

    public void insertAppointmentData(Appointment appointment){
            inpTitle.setText(appointment.getTitle());
            inpDesc.setText(appointment.getDescription());
            inpDate.setValue(appointment.getDate());
            inpFrom.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpTo.setText(appointment.getStart().getHour() + ":" + appointment.getStart().getMinute());
            inpMaxAttend.setText(Integer.toString(appointment.getAttending()));
    }

    public Appointment addAppointment() {
        String title = inpTitle.getText();
        LocalDate date = inpDate.getValue();
        LocalTime startTime = LocalTime.parse(inpFrom.getText());
        LocalTime endTime = LocalTime.parse(inpTo.getText());
        int maxAttending = Integer.parseInt(inpMaxAttend.getText());
        int roomID;
        int owner;

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

        owner = mainApp.getUser().getUserID();

        String description = inpDesc.getText();

        /*
        for (User u : invited) {}
         */

        Appointment appointment = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, maxAttending);
        return appointment;
    }

    @FXML
    void getAllAvailableRooms(){
        validDate();
        validTime();
        validMaxAttend();
        if (validMaxAttend() && validTime() && validDate()) {
            btnRoom.getItems().clear();
            GetData gd = new GetData();
            LocalTime startTime = LocalTime.parse(inpFrom.getText());
            LocalTime endTime = LocalTime.parse(inpTo.getText());
            LocalDate date = inpDate.getValue();
            ArrayList<Room> rooms = gd.getAllAvailableRooms(startTime, endTime, date, Integer.parseInt(inpMaxAttend.getText()));
            for (Room r : rooms) {
                btnRoom.getItems().add(r);
            }
        }
    }
    @FXML
    private void handleSave() {
        validTitle();
        validDate();
        validTime();
        validMaxAttend();
        if (validTitle() && validDate() && validTime() && validMaxAttend()){
            try {
                Appointment app = addAppointment();
                int appointmentID = InsertData.createAppointmentGetID(app);
                app.setAppointmentID(appointmentID);
                for(User usr:invitedUsers){
                    InsertData.inviteUser(usr, app);
                }
                InsertData.inviteUser(mainApp.getUser(),app);
                EditData.acceptInvitation(mainApp.getUser(),app);
                mainApp.showAppointmentView(app);
                mainApp.showUpcomingEvents();
               // for (String line : outInvited.getText().split("\\n")) InsertData.inviteUser(, app);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
        /*
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
        }*/
    }
    ArrayList<User> searchResultsUsers = new ArrayList<>();
    ArrayList<User> invitedUsers = new ArrayList<>();

    @FXML
    private void searchForUser(){
        cmbSearchResults.getItems().clear();
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
                    searchResultsUsers.clear();
                    searchResultsUsers.add(usr);
                    cmbSearchResults.getItems().add(usr.getFullName());
                    //results += usr.getFullName()+"\n";
                }
                cmbSearchResults.show();
            }
            if(groups.size()>0){
                for(Group grp:groups){
                    cmbSearchResults.getItems().add(grp.getName());
                    //results += grp.getName() +"\n";
                }
                cmbSearchResults.show();
            }
        }
    }

    @FXML
    private void addInvitedUser(){
        if (cmbSearchResults.getItems().size() > 0) {
            int index = cmbSearchResults.getSelectionModel().getSelectedIndex();
            invitedUsers.add(searchResultsUsers.get(index));
            ObservableList<Label> obsUsersInvited = FXCollections.observableArrayList();
            for (User usr : invitedUsers) {
                Label lbl = new Label();
                lbl.setId("" + usr.getUserID());
                lbl.setText(usr.getFullName());
                obsUsersInvited.add(lbl);
            }
            inpInvite.clear();
            invitedUsersList.setItems(obsUsersInvited);
        }
    }

    @FXML
    private void handleClose() {
        mainApp.showCalendarView();
    }

    @FXML
    private void setPersonal(){
        inpLocation.setVisible(true);
        btnRoom.setVisible(false);
    }

    @FXML
    private void setWork(){
        inpLocation.setVisible(false);
        btnRoom.setVisible(true);
    }

    private void addErrorStyle(TextField textField){textField.getStyleClass().add("errorTextField");}
    private void removeErrorStyle(TextField textField){textField.getStyleClass().remove("errorTextField");}


    private void activateFocusedProperties() {
        inpTitle.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                removeErrorStyle(inpTitle);
                if (!inpTitle.getText().isEmpty()) {
                    validTitle();
                }
            }
        });
        inpDate.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!(inpDate.getValue() == null)) {
                    validDate();
                }
            }
        });
        inpFrom.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                removeErrorStyle(inpFrom);
                if (!inpFrom.getText().isEmpty() && !inpTo.getText().isEmpty()) {
                    validTime();
                }
                if (!inpFrom.getText().matches("\\d\\d:\\d\\d")) {
                    addErrorStyle(inpFrom);
                }
            }
        });
        inpTo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                removeErrorStyle(inpTo);
                if (!inpTo.getText().matches("\\d\\d:\\d\\d")) {
                    addErrorStyle(inpTo);
                }
                if (!inpFrom.getText().isEmpty() && !inpTo.getText().isEmpty()) {
                    validTime();
                }
            }
        });
        inpMaxAttend.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                removeErrorStyle(inpMaxAttend);
                if (!inpMaxAttend.getText().isEmpty()) {
                    validMaxAttend();
                }
            }
        });
    }

    public boolean validTime() {
        boolean noError = true;
        if (inpFrom.getText().isEmpty()){
            addErrorStyle(inpFrom);
            noError = false;
        }
        if (inpTo.getText().isEmpty()){
            addErrorStyle(inpTo);
            noError = false;
        }
        if (!inpTo.getText().matches("\\d\\d:\\d\\d")) {
            addErrorStyle(inpTo);
            noError = false;
        }
        if (!inpFrom.getText().matches("\\d\\d:\\d\\d")){
            addErrorStyle(inpFrom);
            noError = false;
        }
        if (!inpTo.getText().isEmpty() && LocalTime.parse(inpTo.getText()).compareTo(LocalTime.parse(inpFrom.getText())) == -1){
            addErrorStyle(inpTo);
            noError = false;
        }
        if (!inpFrom.getText().isEmpty() && LocalTime.parse(inpTo.getText()).equals(LocalTime.parse(inpFrom.getText()))) {
            addErrorStyle(inpFrom);
            noError = false;
        }
        removeErrorStyle(inpFrom);
        removeErrorStyle(inpTo);
        return noError;
    }

    private boolean validDate(){
        if (inpDate.getValue() == null || inpDate.getValue().isBefore(LocalDate.now())) {
            inpDate.setStyle("-fx-border-color: red" + "; -fx-border-width: 1px;");
            inpDate.setPromptText("Must be a date in the future or today");
            return false;
        }
        inpDate.setStyle("-fx-border-width: 0px;");
        return true;

    }

    public boolean validTitle() {
        if (inpTitle.getText().isEmpty() || inpTitle.getText().length() > 40) {
            addErrorStyle(inpTitle);
            return false;
        }
        removeErrorStyle(inpTitle);
        return true;
    }

    private boolean validMaxAttend(){
        try{
            if (inpMaxAttend.getText().isEmpty()){
                addErrorStyle(inpMaxAttend);
                return false;
            }
            int maxAttend = Integer.parseInt(inpMaxAttend.getText());
            if (maxAttend < 0){
                addErrorStyle(inpMaxAttend);
                inpMaxAttend.clear();
                inpMaxAttend.setPromptText("Must be a positive number!");
                return false;
            }
            removeErrorStyle(inpMaxAttend);
            return true;
        } catch (NumberFormatException e) {
            addErrorStyle(inpMaxAttend);
            inpMaxAttend.clear();
            inpMaxAttend.setPromptText("Must be a number!");
            return false;
        }
    }
}