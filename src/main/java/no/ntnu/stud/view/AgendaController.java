package no.ntnu.stud.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kristoffer on 11.03.15.
 */
public class AgendaController {
    private MainApp mainApp;

    @FXML
    private Label lblAppointments;

    @FXML
    private ListView listAppointmets;

    public AgendaController(){

    }

    public void renderAgenda(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyy-MM-dd");
        ObservableList<Label> appointments = FXCollections.observableArrayList();
        String dateString = dateFormat.format(calendar.getTime());
        lblAppointments.setText(dateString);
        GetData gd = new GetData();
        ArrayList<Appointment> apps = gd.getAppointments(mainApp.getUser(), dbFormat.format(calendar.getTime()));

        for(Appointment app: apps){
            appointments.add(createLabel(app));
        }

    }

    private Label createLabel(Appointment appointment){
        Label lbl = new Label();
        lbl.setText(appointment.getStart().toString()+ " - "+appointment.getTitle());
        lbl.setOnMouseClicked((event) -> {
            mainApp.showAppointmentView(appointment);
        });

        return lbl;
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
