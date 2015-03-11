package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private Label from, to , date, type, maxAtt, loc, locationLabel, fromTo, lblTitle;

    @FXML
    private TextArea inpDesc, invited;

    @FXML
    private Button btnSave, btnClose;

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
        String invitedStr ="";
        for(User usr:users){
            invitedStr += usr.getFullName()+"\n";
        }
        invited.setMaxHeight(40*users.size());
        invited.setText(invitedStr);
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
