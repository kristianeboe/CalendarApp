package no.ntnu.stud.view;

import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
    AnchorPane ap;

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
        int counter = 0;
        GridPane gp = new GridPane();
        gp.setPrefHeight(65);
        gp.setPrefWidth(175);
        gp.setMinWidth(175);
        gp.setLayoutX(0);
        gp.getColumnConstraints().add(new ColumnConstraints(100));
        gp.getColumnConstraints().add(new ColumnConstraints(100));
        gp.getRowConstraints().add(new RowConstraints(49));
        gp.getRowConstraints().add(new RowConstraints(15));
        gp.setLayoutY(75*counter);

        Button acceptBtn = new Button();
        acceptBtn.setText("✓");
        acceptBtn.maxHeight(25);
        acceptBtn.maxWidth(25);

        Button declineBtn = new Button();
        declineBtn.setText("x");
        declineBtn.maxHeight(25);
        declineBtn.maxWidth(25);

        GridPane.setValignment(declineBtn, VPos.BOTTOM);
        GridPane.setHalignment(declineBtn, HPos.LEFT);
        GridPane.setValignment(acceptBtn, VPos.BOTTOM);
        GridPane.setConstraints(acceptBtn, 1, 0);
        GridPane.setConstraints(declineBtn, 1,0);


        Separator separator = new Separator();
        separator.setPrefWidth(30);

        GridPane.setConstraints(separator, 0,1,2,1);

        Label label = new Label();
        label.getStyleClass().add("meetings");
        label.setText("Meeting");
        label.maxWidth(140);

        GridPane.setConstraints(label, 0,0,2,1);

        gp.getChildren().addAll(acceptBtn, declineBtn, label, separator);

        ap.getChildren().add(gp);

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
