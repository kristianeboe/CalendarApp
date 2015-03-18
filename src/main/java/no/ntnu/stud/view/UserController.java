package no.ntnu.stud.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Group;
import no.ntnu.stud.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by kristoffer on 12.03.15.
 */
public class UserController {
    private MainApp mainApp;

    @FXML
    ListView appointmentList, groupList;

    @FXML
    Label name;

    public void renderUser(int userID){
        GetData gd = new GetData();
        User user = gd.getUser(userID);
        name.setText(user.getFullName());

        //RENDER APPOINTMENTS START
        ArrayList<Appointment> appointments = gd.getAppointments(user,10000,true);

        ObservableList<Label> obsAppointments = FXCollections.observableArrayList();

        final EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int appointmentID = Integer.parseInt(clickedLabel.getId());
                mainApp.showAppointmentView(appointmentID);
            }
        };

        for(Appointment app:appointments){
            if((app.getDate().compareTo(LocalDate.now())<=0)){
                continue;
            }
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + app.getAppointmentID());
            lbl.setText(app.getDate().toString()+" "+ app.getStart()+" "+app.getTitle());
            obsAppointments.add(lbl);
        }
        appointmentList.setItems(obsAppointments);

        //RENDER APPOINTMENTS END

        //RENDER GROUPS START
        ArrayList<Group> groups = gd.getGroups(user, true);
        groups.addAll(gd.getGroups(user, false));

        ObservableList<Label> obsGroups = FXCollections.observableArrayList();

        final EventHandler<MouseEvent> clickHandlerGroups = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int groupID = Integer.parseInt(clickedLabel.getId());
                mainApp.showGroup(groupID);
            }
        };

        for(Group grp:groups){
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandlerGroups);
            lbl.setId("" + grp.getGroupID());
            lbl.setText(grp.getName());
            obsGroups.add(lbl);
        }
        groupList.setItems(obsGroups);

    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }
}
