package no.ntnu.stud.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by kristoffer on 11.03.15.
 */
public class ViewAppointmentController {
    private MainApp mainApp;
    Logger logger = Logger.getLogger("ViewAppointmentCtrl");

    @FXML
    Label from, to , date, type, maxAtt, loc, locationLabel, fromTo, lblTitle;

    @FXML
    TextArea inpDesc, invited;

    @FXML
    Button btnSave, btnClose;

    @FXML
    ListView invitedList;

    public ViewAppointmentController(){

    }

    public void renderViewAppointment(int appointmentID){
        renderAppointment(GetData.getAppointment(appointmentID));
    }

    public void renderAppointment(Appointment appointment){
        boolean isOwner = mainApp.getUser().getUserID() == appointment.getOwner();

        if (isOwner) {
            logger.debug("User is owner");
            renderEditAppointment(appointment);
        } else {
            logger.debug("User is participant");
            renderViewAppointment(appointment);
        }
    }

    public void renderViewAppointment(Appointment appointment) {
        GetData gd = new GetData();
        // Render view
        lblTitle.setText(appointment.getTitle());
        fromTo.setText(appointment.getStart().toString()+"-"+appointment.getEnd().toString());
        date.setText(appointment.getDate().toString());
        maxAtt.setText(""+appointment.getAttending());
        if(appointment.getLocation() == null && appointment.getRoomID() >-1){
            locationLabel.setText("Room");
            loc.setText(gd.getRoom(appointment.getRoomID()).getName());
        }else{
            loc.setText(appointment.getLocation());
        }
        inpDesc.setText(appointment.getDescription());
        ArrayList<User> invitedUsers = gd.getInvited(appointment);
        ArrayList<User> acceptedUsers = gd.getAccepted(appointment);
        ArrayList<User> declinedUsers = gd.getDeclined(appointment);
        ObservableList<Label> obsUsers = FXCollections.observableArrayList();

        final EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int userID = Integer.parseInt(clickedLabel.getId());
                mainApp.showUser(userID);
            }
        };
        for(User usr:acceptedUsers){
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getUserID());
            lbl.setText("[Going] " + usr.getFullName());
            obsUsers.add(lbl);
        }
        for(User usr:invitedUsers){
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getUserID());
            lbl.setText("[Invited] "+usr.getFullName());
            obsUsers.add(lbl);
        }
        for(User usr:declinedUsers){
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getUserID());
            lbl.setText("[Not going] "+usr.getFullName());
            obsUsers.add(lbl);
        }
        invitedList.setItems(obsUsers);

        // Access control
        inpDesc.setEditable(false);
        btnSave.setVisible(false);
    }

    public void renderEditAppointment(Appointment appointment) {
        logger.debug("Rendering " + appointment + " as editable");
        mainApp.showAppointmentDialog(appointment);
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
