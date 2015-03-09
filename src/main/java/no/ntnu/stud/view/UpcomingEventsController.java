package no.ntnu.stud.view;

import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;
import org.controlsfx.control.spreadsheet.Grid;

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
        ArrayList<Appointment> appointments = gd.getAppointments(mainApp.getUser(),5);
        ArrayList<Appointment> invitations = gd.getInvitations(mainApp.getUser().getUserID());
        int counter = 0;
        for (int i = 0; i < invitations.size(); i++){
            if(counter > 10) break;
            addEvent(invitations.get(i).getTitle(),counter);
            counter++;
        }


    }

    private void addEvent(String labelText, int position){
        GridPane gp = new GridPane();
        gp.setPrefHeight(65);
        gp.setPrefWidth(175);
        gp.setMinWidth(175);
        gp.setLayoutX(0);
        gp.getColumnConstraints().add(new ColumnConstraints(97));
        gp.getColumnConstraints().add(new ColumnConstraints(97));
        gp.getRowConstraints().add(new RowConstraints(49));
        gp.getRowConstraints().add(new RowConstraints(15));
        gp.setLayoutY(75*position);

        Button acceptBtn = new Button();
        acceptBtn.setText("✓");
        acceptBtn.maxHeight(25);
        acceptBtn.prefHeight(25);
        acceptBtn.maxWidth(25);
        acceptBtn.prefWidth(25);
        acceptBtn.getStyleClass().add("acceptButton");

        Button declineBtn = new Button();
        declineBtn.setText("x");
        declineBtn.maxHeight(25);
        declineBtn.prefHeight(25);
        declineBtn.maxWidth(25);
        declineBtn.prefWidth(25);
        declineBtn.getStyleClass().add("declineButton");


        GridPane.setValignment(declineBtn, VPos.BOTTOM);
        GridPane.setHalignment(declineBtn, HPos.RIGHT);
        GridPane.setValignment(acceptBtn, VPos.BOTTOM);
        GridPane.setHalignment(acceptBtn, HPos.LEFT);
        GridPane.setMargin(acceptBtn, new Insets(0,0,2,40));
        GridPane.setMargin(declineBtn, new Insets(0,0,2,75));
        GridPane.setConstraints(acceptBtn, 1, 0);
        GridPane.setConstraints(declineBtn, 1,0);


        Separator separator = new Separator();
        separator.setPrefWidth(30);

        GridPane.setHalignment(separator, HPos.CENTER);
        GridPane.setValignment(separator, VPos.TOP);
        GridPane.setMargin(separator, new Insets(5,0,0,0));

        GridPane.setConstraints(separator, 0,1,2,1);

        Label label = new Label();
        label.getStyleClass().add("meetings");
        label.setText("Meeting");
        label.maxWidth(140);
        label.setAlignment(Pos.TOP_LEFT);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setValignment(label, VPos.BOTTOM);
        GridPane.setMargin(label, new Insets(0,60,0,0));

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
