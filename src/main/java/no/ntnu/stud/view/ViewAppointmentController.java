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

import java.util.ArrayList;

/**
 * Created by kristoffer on 11.03.15.
 */
public class ViewAppointmentController {
    private MainApp mainApp;

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

    public void renderViewAppointment(Appointment appointment){
        GetData gd = new GetData();
        if(mainApp.getUser().getUserID() != appointment.getOwner()){
            inpDesc.setEditable(false);
            btnSave.setVisible(false);
        }
        inpDesc.setEditable(false);
        btnSave.setVisible(false);
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
        ArrayList<User> users = gd.getInvited(appointment);
        ObservableList<Label> obsUsers = FXCollections.observableArrayList();

        final EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int userID = Integer.parseInt(clickedLabel.getId());
                //mainApp.editGroup(groupID);
            }
        };
        for(User usr:users){
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getUserID());
            lbl.setText(usr.getFullName());
            obsUsers.add(lbl);
        }
        invitedList.setItems(obsUsers);
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
