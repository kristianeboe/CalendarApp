package no.ntnu.stud.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.EditData;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kristian on 03/03/15.
 */
public class NewAppointmentController {
    private MainApp mainApp;
    private Stage newAppointmentStage;
    private Appointment appointment;
    Logger logger = Logger.getLogger("NewAppointmentCtrl");

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
    @FXML
    private Button btnAddReminder;
    @FXML
    private Button btnRemoveReminder;
    @FXML
    private TextField inpReminder;
    @FXML
    private ComboBox<String> inpReminderType;
    @FXML
    private Button btnAddReminder1;
    @FXML
    private Button btnRemoveReminder1;
    @FXML
    private TextField inpReminder1;
    @FXML
    private ComboBox<String> inpReminderType1;
    @FXML
    private Button btnAddReminder2;
    @FXML
    private Button btnRemoveReminder2;
    @FXML
    private TextField inpReminder2;
    @FXML
    private ComboBox<String> inpReminderType2;

    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    public NewAppointmentController() {

    }

    @FXML
    private void initialize() {
        activateFocusedProperties();
    }

    public void setNewAppointmentStage(Stage newAppointmentStage) {
        this.newAppointmentStage = newAppointmentStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void insertAppointmentData(Appointment appointment) {
        inpTitle.setText(appointment.getTitle());
        inpDesc.setText(appointment.getDescription());
        inpDate.setValue(appointment.getDate());
        this.appointment = appointment;

        String fromTime = appointment.getStart().format(timeFormat);
        logger.trace("fromTime: " + appointment.getStart() + " | formatted: " + fromTime);
        inpFrom.setText(fromTime);
        String toTime = appointment.getEnd().format(timeFormat);
        logger.trace("toTime: " + appointment.getEnd() + " | formatted: " + toTime);
        inpTo.setText(toTime);
        inpMaxAttend.setText(Integer.toString(appointment.getAttending()));
        btnRoom.setValue(GetData.getRoomById(appointment.getRoomID()));

        List<Alarm> alarms = mainApp.getAlarms(mainApp.getUser().getUserID(), appointment.getAppointmentID());
        switch(alarms.size()) {
            case 0:
                break;
            case 3:
                handleAddReminder2();
                inpReminder2.setText(String.valueOf(alarms.get(2).getNumberOfType()));
                inpReminderType2.setValue(alarms.get(2).getType());
            case 2:
                handleAddReminder1();
                inpReminder1.setText(String.valueOf(alarms.get(1).getNumberOfType()));
                inpReminderType1.setValue(alarms.get(1).getType());
            case 1:
                handleAddReminder();
                inpReminder.setText(String.valueOf(alarms.get(0).getNumberOfType()));
                inpReminderType.setValue(alarms.get(0).getType());
                break;
        }

        logger.debug("Adding invited users to box");
        logger.trace(GetData.getInvited(appointment));
        addInvitedToBox(GetData.getInvited(appointment));
    }

    public Appointment addAppointment() {
        String title = inpTitle.getText();
        LocalDate date = inpDate.getValue();
        LocalTime startTime = LocalTime.parse(inpFrom.getText());
        LocalTime endTime = LocalTime.parse(inpTo.getText());
        int maxAttending = Integer.parseInt(inpMaxAttend.getText());
        int roomID;
        int owner;
        String location;

        // If roomID, use roomID. If not, use location.

        // Cleanest hack ever to find roomID
        if (radioWork.isSelected()) {
            location = "";
            String roomValue = btnRoom.getValue().toString();
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher m = pattern.matcher(roomValue);

            if (m.find()) {
                roomID = Integer.parseInt(m.group(1));
                // System.out.println("match: " + m.group(1));
            } else {
                throw new IllegalArgumentException("FUCK YOU DIDNT FIND THE ROOM SHIT");
            }
        } else{
            roomID = -1;
            location = inpLocation.getText();
        }

        owner = mainApp.getUser().getUserID();

        String description = inpDesc.getText();

        /*
        for (User u : invited) {}
         */

        Appointment appointment = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, maxAttending);

        if (!inpReminder.getText().equals("")) {
            logger.info("inpReminder.getTest(): " + inpReminder.getText());
            logger.info("inpReminderType.getValue(): " + inpReminderType.getValue());
            mainApp.addAlarm(new Alarm(mainApp.getUser(), appointment, inpReminder.getText(), inpReminderType.getValue()));
        } else {
            return appointment;
        }

        if (!inpReminder1.getText().equals("")) {
            mainApp.addAlarm(new Alarm(mainApp.getUser(), appointment, inpReminder1.getText(), inpReminderType.getValue()));
        } else {
            return appointment;
        }

        if (!inpReminder2.getText().equals("")) {
            mainApp.addAlarm(new Alarm(mainApp.getUser(), appointment, inpReminder2.getText(), inpReminderType2.getValue()));
        } else {
            return appointment;
        }
        return appointment;
    }

    @FXML
    void getAllAvailableRooms() {
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
        logger.trace("Clicked save");
        validTitle();
        validDate();
        validTime();
        validMaxAttend();
        if (validTitle() && validDate() && validTime() && validMaxAttend()) {
            logger.trace("Valid inputs");
            Appointment new_appointment = addAppointment();
            logger.debug("Created appointment instance");
            try {
                insertOrUpdateAppointment(new_appointment, mainApp.getUser());
                mainApp.showAppointmentView(new_appointment);
                mainApp.showUpcomingEvents();
                // for (String line : outInvited.getText().split("\\n")) InsertData.inviteUser(, app);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Appointment insertOrUpdateAppointment(Appointment appointment, User user) {
        logger.trace("Insert or update appointment");
        if (this.appointment == null || this.appointment.getAppointmentID() == -1) {
            logger.debug("Creating new appointment");
            Appointment new_appointment = InsertData.createAppointment(appointment);
            EditData.removeAlarms(mainApp.getUser().getUserID(), new_appointment.getAppointmentID());
            InsertData.setAlarms(mainApp.getAlarms(mainApp.getUser().getUserID(), new_appointment.getAppointmentID()));
            InsertData.inviteUser(user, appointment);
            EditData.acceptInvitation(user, appointment);
            for (User usr : invitedUsers) {
                logger.trace("Inviting " + usr + " to " + appointment);
                InsertData.inviteUser(usr, appointment);
            }
            return new_appointment;
        } else {
            logger.debug("Updating existing appointment");
            appointment.setAppointmentID(this.appointment.getAppointmentID());
            Appointment edited_appointment = EditData.editAppointment(appointment);
            EditData.removeAlarms(mainApp.getUser().getUserID(), edited_appointment.getAppointmentID());
            InsertData.setAlarms(mainApp.getAlarms(mainApp.getUser().getUserID(), edited_appointment.getAppointmentID()));
            Notification notification = new Notification(edited_appointment, "Something changed");
            notification = notification.create();
            logger.debug("Notifying users about change to " + edited_appointment);
            logger.trace("invitedUsers-list: " + invitedUsers);
            for (User invitedUser : invitedUsers) {
                logger.trace("Notifying " + invitedUser + " about change to " + edited_appointment);
                InsertData.notifyUser(notification, invitedUser);
            }
            return edited_appointment;
        }
    }

    @FXML
    private void handleEdit() {
        if (validTitle() && validDate() && validTime() && validMaxAttend()) {
            Appointment app = addAppointment();

        }
    }

    ArrayList<User> searchResultsUsers = new ArrayList<>();
    ArrayList<User> invitedUsers = new ArrayList<>();

    ArrayList<Group> searchResultsGroups = new ArrayList<>();
    ArrayList<Group> invitedGroups = new ArrayList<>();

    ObservableList<Label> obsInvited = FXCollections.observableArrayList();


    @FXML
    private void searchForUser() {
        cmbSearchResults.getItems().clear();
        GetData gd = new GetData();
        String partOfName = inpInvite.getText();
        ArrayList<User> users;
        ArrayList<Group> groups;
        if (partOfName.length() > 2) {
            groups = gd.searchGroup(partOfName);
            users = gd.searchUser(partOfName);
            if (users.size() > 0) {
                searchResultsGroups.clear();
                searchResultsUsers.clear();
                for (User usr : users) {
                    if (usr.getUserID() != (mainApp.getUser().getUserID())) {
                        searchResultsUsers.add(usr);
                        cmbSearchResults.getItems().add(usr.getFullName());
                    }
                }
                cmbSearchResults.show();
            }else if (groups.size() > 0) {
                searchResultsGroups.clear();
                searchResultsUsers.clear();
                for (Group grp : groups) {
                    searchResultsGroups.add(grp);
                    cmbSearchResults.getItems().add(grp.getName());
                }
                cmbSearchResults.show();
            }
        }
    }

    @FXML
    private void addInvitedUser() {
        if (cmbSearchResults.getItems().size() > 0) {
            int index = cmbSearchResults.getSelectionModel().getSelectedIndex();
            if(!searchResultsUsers.isEmpty()){
                User usr = searchResultsUsers.get(index);
                invitedUsers.add(usr);
                addUserToInvitedBox(usr);
                inpInvite.clear();
            }else if(!searchResultsGroups.isEmpty()){
                Group grp = searchResultsGroups.get(index);
                invitedGroups.add(grp);
                addGroupToInvitedBox(grp);
                inpInvite.clear();
            }
            //invitedUsersList.getItems().clear();
            invitedUsersList.setItems(obsInvited);

        }
    }

    public void addInvitedToBox(ArrayList<Inevitable> inviteables) {
        for (Inevitable invited : inviteables) {
            if (invited.getClass().equals(Group.class)) {
                logger.trace("Adding group \"" + invited.getName() + "\" to box");
                addGroupToInvitedBox((Group) invited);
            }
            else if (invited.getClass().equals(User.class)) {
                logger.trace("Adding user \"" + invited.getName() + "\" to box");
                addUserToInvitedBox((User) invited);
                logger.trace("Adding user \"" + invited.getName() + "\" to invitedUsersr");
                invitedUsers.add((User) invited);
            }
        }
        logger.debug("Invited users: (" + obsInvited.size() + ") " + obsInvited.toString());
        invitedUsersList.setItems(obsInvited);
    }

    private void addUserToInvitedBox(User user) {
        int status = GetData.userIsAvailable(user,LocalTime.parse(inpFrom.getText()),LocalTime.parse(inpTo.getText()),inpDate.getValue());
        Label lbl = new Label();
        if(status == 2){
            lbl.setTextFill(Color.RED);
        }else if(status == 1){
            lbl.setTextFill(Color.GREEN);
        }else{
            lbl.setTextFill(Color.ORANGE);
        }
        lbl.setId("" + user.getUserID());
        lbl.setText(user.getFullName());
        obsInvited.add(lbl);
    }

    private void addGroupToInvitedBox(Group group) {
        Label lbl = new Label();
        lbl.setId("" + group.getGroupID());
        lbl.setText(group.getName());
        obsInvited.add(lbl);
    }

    @FXML
    private void handleClose() {
        mainApp.showCalendarView();
    }

    @FXML
    private void setPersonal() {
        inpMaxAttend.setVisible(false);
        inpLocation.setVisible(true);
        btnRoom.setVisible(false);
    }

    @FXML
    private void setWork() {
        inpMaxAttend.setVisible(true);
        inpLocation.setVisible(false);
        btnRoom.setVisible(true);
    }

    private void addErrorStyle(TextField textField) {
        textField.getStyleClass().add("errorTextField");
    }

    private void removeErrorStyle(TextField textField) {
        textField.getStyleClass().remove("errorTextField");
    }


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
                String time = inpFrom.getText();
                if(time.indexOf(":") != -1){
                    if (time.substring(0, time.indexOf(":")).length() < 2){
                        inpFrom.setText("0"+time);
                    }
                    if(time.substring(time.indexOf(":")+1).length() < 2){
                        inpFrom.setText(time+"0");
                    }
                }
                if (!inpFrom.getText().isEmpty() && !inpTo.getText().isEmpty()) {
                    validTime();
                }
                if (!inpFrom.getText().isEmpty() && !inpFrom.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                    addErrorStyle(inpFrom);
                }
            }
        });
        inpTo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                String time = inpTo.getText();
                if(time.indexOf(":") != -1){
                    if (time.substring(0, time.indexOf(":")).length() < 2){
                        inpTo.setText("0"+time);
                    }
                    if(time.substring(time.indexOf(":")+1).length() < 2){
                        inpTo.setText(time+"0");
                    }
                }
                if (!inpFrom.getText().isEmpty() && !inpTo.getText().isEmpty()) {
                    validTime();
                }
                if (!inpTo.getText().isEmpty() && !inpTo.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                    addErrorStyle(inpTo);
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
        if (inpFrom.getText().isEmpty()) {
            addErrorStyle(inpFrom);
            noError = false;
        }
        if (inpTo.getText().isEmpty()) {
            addErrorStyle(inpTo);
            noError = false;
        }
        if (!inpFrom.getText().matches("\\d?\\d:\\d?\\d") || !inpTo.getText().matches("\\d?\\d:\\d?\\d") || !inpTo.getText().isEmpty() && LocalTime.parse(inpTo.getText()).compareTo(LocalTime.parse(inpFrom.getText())) == -1) {
            addErrorStyle(inpTo);
            noError = false;
        }
        if (!inpFrom.getText().matches("\\d?\\d:\\d?\\d") || !inpTo.getText().matches("\\d?\\d:\\d?\\d") || !inpFrom.getText().isEmpty() && LocalTime.parse(inpTo.getText()).equals(LocalTime.parse(inpFrom.getText()))) {
            addErrorStyle(inpFrom);
            noError = false;
        }
        if (noError){
            removeErrorStyle(inpFrom);
            removeErrorStyle(inpTo);
        }
        return noError;
    }

    private boolean validDate() {
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

    private boolean validMaxAttend() {
        try {
            if (inpMaxAttend.getText().isEmpty()) {
                addErrorStyle(inpMaxAttend);
                return false;
            }
            int maxAttend = Integer.parseInt(inpMaxAttend.getText());
            if (maxAttend < 0) {
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

    public void renderEditView(Appointment appointment) {
        // Populate fields
        insertAppointmentData(appointment);

        // Set access control
    }

    public void handleAddReminder() {
        inpReminder.setVisible(true);
        inpReminderType.setVisible(true);
        btnAddReminder.setVisible(false);
        btnRemoveReminder.setVisible(true);

        btnAddReminder1.setVisible(true);
    }

    public void handleRemoveReminder() {
        if (inpReminder1.visibleProperty().getValue()) {
            inpReminder.setText(inpReminder1.getText());
            inpReminderType.setValue(inpReminderType1.getValue());
            handleRemoveReminder1();
        } else {
            inpReminder.clear();
            btnAddReminder1.setVisible(false);
            inpReminder.setVisible(false);
            inpReminderType.setVisible(false);
            btnAddReminder.setVisible(true);
            btnRemoveReminder.setVisible(false);
        }
    }

    public void handleAddReminder1() {
        inpReminder1.setVisible(true);
        inpReminderType1.setVisible(true);
        btnAddReminder1.setVisible(false);
        btnRemoveReminder1.setVisible(true);

        btnAddReminder2.setVisible(true);
    }

    public void handleRemoveReminder1() {
        if (inpReminder2.visibleProperty().getValue()) {
            inpReminder1.setText(inpReminder2.getText());
            inpReminderType1.setValue(inpReminderType2.getValue());
            inpReminder2.clear();
            inpReminder2.setVisible(false);
            inpReminderType2.setVisible(false);
            btnAddReminder2.setVisible(true);
            btnRemoveReminder2.setVisible(false);
        } else {
            inpReminder1.clear();
            btnAddReminder2.setVisible(false);
            inpReminder1.setVisible(false);
            inpReminderType1.setVisible(false);
            btnAddReminder1.setVisible(true);
            btnRemoveReminder1.setVisible(false);
        }
    }

    public void handleAddReminder2() {
        inpReminder2.setVisible(true);
        inpReminderType2.setVisible(true);
        btnAddReminder2.setVisible(false);
        btnRemoveReminder2.setVisible(true);
    }

    public void handleRemoveReminder2() {
        inpReminder2.setVisible(false);
        inpReminderType2.setVisible(false);
        inpReminder2.clear();
        btnAddReminder2.setVisible(true);
        btnRemoveReminder2.setVisible(false);
    }
}