package no.ntnu.stud.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import no.ntnu.stud.model.Inevitable;
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
    Label from, to , date, type, maxAtt, loc, locationLabel, fromTo, lblTitle, attLbl;

    @FXML
    TextArea inpDesc, invited;

    @FXML
    Button btnSave, btnClose, editBtn;

    @FXML
    ListView invitedList;

    public ViewAppointmentController(){

    }

    public void renderViewAppointment(int appointmentID){
        renderAppointment(GetData.getAppointment(appointmentID));
    }

    public void renderAppointment(Appointment appointment){
        boolean isOwner = mainApp.getUser().getUserID() == appointment.getOwner();
        renderViewAppointment(appointment);
        /*if (isOwner) {
            logger.debug("User is owner");
            renderEditAppointment(appointment);
        } else {
            logger.debug("User is participant");

        }*/
    }

    public void renderViewAppointment(Appointment appointment) {
        GetData gd = new GetData();
        // Render view
        lblTitle.setText(appointment.getTitle());
        fromTo.setText(appointment.getStart().toString()+"-"+appointment.getEnd().toString());
        date.setText(appointment.getDate().toString());
        maxAtt.setText(""+appointment.getAttending());
        if(appointment.getLocation() == null && appointment.getRoomID() >0){
            locationLabel.setText("Room");
            loc.setText(gd.getRoom(appointment.getRoomID()).getName());
        }else{
            maxAtt.setVisible(false);
            attLbl.setVisible(false);
            loc.setText(appointment.getLocation());
        }
        inpDesc.setText(appointment.getDescription());
        ArrayList<Inevitable> invitedUsers = gd.getInvited(appointment);
        ArrayList<Inevitable> acceptedUsers = gd.getAccepted(appointment);
        ArrayList<Inevitable> declinedUsers = gd.getDeclined(appointment);
        ObservableList<Label> obsUsers = FXCollections.observableArrayList();

        final EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int userID = Integer.parseInt(clickedLabel.getId());
                mainApp.showUser(userID);
            }
        };
        for(Inevitable usr:acceptedUsers){
            logger.trace("Accepted: " + usr);
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getId());
            lbl.setText("[Going] " + usr.getName());
            obsUsers.add(lbl);
        }
        for(Inevitable usr:invitedUsers){
            logger.trace("Invited: " + usr);
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getId());
            lbl.setText("[Invited] "+usr.getName());
            obsUsers.add(lbl);
        }
        for(Inevitable usr:declinedUsers){
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getId());
            lbl.setText("[Not going] "+usr.getName());
            obsUsers.add(lbl);
        }
        invitedList.setItems(obsUsers);

        // Access control
        inpDesc.setEditable(false);
        btnSave.setVisible(false);

        //Edit Button
        final EventHandler<ActionEvent> editClickHandler = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                renderEditAppointment(appointment);
            }
        };

        editBtn.setOnAction(editClickHandler);

        if(mainApp.getUser().getUserID()!= appointment.getOwner()){
            editBtn.setVisible(false);
        }
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
