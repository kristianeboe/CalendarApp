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
import no.ntnu.stud.model.Group;
import no.ntnu.stud.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kristoffer on 12.03.15.
 */
public class GroupViewController {

    private MainApp mainApp;

    @FXML
    Label lblName;

    @FXML
    ListView memberList;

    public void renderGroup(int groupID){
        GetData gd = new GetData();
        Group group = gd.getGroup(groupID);
        lblName.setText(group.getName());

        ArrayList<User> users = gd.getUsersInGroup(groupID);
        ObservableList<Label> obsUsers = FXCollections.observableArrayList();

        final EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int userID = Integer.parseInt(clickedLabel.getId());
                mainApp.showUser(userID);
            }
        };
        for(User usr:users){
            Label lbl = new Label();
            lbl.setOnMouseClicked(clickHandler);
            lbl.setId("" + usr.getUserID());
            lbl.setText(usr.getFullName());
            obsUsers.add(lbl);
        }
        memberList.setItems(obsUsers);
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
