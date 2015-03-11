package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.model.Appointment;

/**
 * Created by kristoffer on 11.03.15.
 */
public class ViewAppointmentController {
    private MainApp mainApp;

    @FXML
    private Label from, to , date, type, maxAtt, locationLabel, fromTo;

    @FXML
    private TextArea inpDesc;

    public ViewAppointmentController(){

    }

    public void renderViewAppointment(Appointment appointment){
        fromTo.setText(appointment.getStart().toString()+"-"+appointment.getEnd().toString());
        date.setText(appointment.getDate().toString());
        maxAtt.setText(""+appointment.getAttending());
        locationLabel.setText(appointment.getLocation());
        inpDesc.setText(appointment.getDescription());

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
