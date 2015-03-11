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

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kristoffer on 11.03.15.
 */
public class MyGroupsController {
    private MainApp mainApp;

    @FXML
    ListView ownerList, memberList;

    public MyGroupsController(){

    }

    public void renderMyGroups(){
        GetData gd = new GetData();
        //Find group where user is owner
        ArrayList<Group> ownedGroups = gd.getGroups(mainApp.getUser(),true);

        ObservableList<Label> ownedObsList = FXCollections.observableArrayList();

        final EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int groupID = Integer.parseInt(clickedLabel.getId());
                mainApp.editGroup(groupID);
            }
        };

        for(Group grp: ownedGroups){
            Label lbl = new Label(grp.getName());
            lbl.setId(""+grp.getGroupID());
            lbl.setOnMouseClicked(clickHandler);
            ownedObsList.add(lbl);
        }
        ownerList.setItems(ownedObsList);

        //Find groups where user is member
        ArrayList<Group> memberGroups = gd.getGroups(mainApp.getUser(),false);

        ObservableList<Label> memberObsList = FXCollections.observableArrayList();

        for(Group grp: memberGroups){
            Label lbl = new Label(grp.getName());
            memberObsList.add(lbl);
        }
        memberList.setItems(memberObsList);
    }


    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
