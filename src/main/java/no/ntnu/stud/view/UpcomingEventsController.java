package no.ntnu.stud.view;

import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by Kristian on 02/03/15.
 */
public class UpcomingEventsController {

    private MainApp mainApp;
    private Appointment appointment;

    GetData gd = new GetData();
    MainApp ma = new MainApp();

    @FXML
    private Button btnNewAppointment, btnEditAppointment;

    @FXML ScrollPane scrollPane;

    @FXML
    private GridPane upcomingEvents;

    public UpcomingEventsController(){

    }

    @FXML
    private void initialize(){
    }

    public void renderUpcomingAppointments(){
        /*appointment = new Appointment("Title",LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1),mainApp.getUser(),"desc123","loc123",3, 10);
        ArrayList<Appointment> appointments = gd.getAppointments(mainApp.getUser(),3);
        firstMeeting.setText("");
        secondMeeting.setText("");
        thirdMeeting.setText("");
        if(appointments.size() == 0){
            firstMeeting.setText("No upcoming meetings");
            firstSeparator.setVisible(true);
        }else{
            for(int i = 0; i < appointments.size();i++){
                if(i == 0){
                    firstMeeting.setText(appointments.get(i).getTitle());
                    firstMeeting.setId("" + appointments.get(i).getAppointmentID());
                    firstSeparator.setVisible(true);
                }else if (i == 1){
                    secondMeeting.setText(appointments.get(i).getTitle());
                    secondMeeting.setId(""+appointments.get(i).getAppointmentID());
                    secondSeparator.setVisible(true);
                }else{
                    thirdMeeting.setText(appointments.get(i).getTitle());
                    thirdMeeting.setId(""+appointments.get(i).getAppointmentID());
                    thirdSeparator.setVisible(true);
                }
            }
        }*/

        GridPane gp = new GridPane();
    }

    @FXML
    void handleAccept(){

    }

    @FXML
    void handleDecline(){

    }

    @FXML
    public void handleAddEvent(){
        mainApp.showAppointmentDialog(null);
    }

    @FXML
    public void handleEditEvent(){
        mainApp.showAppointmentDialog(appointment);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
