package no.ntnu.stud.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Group;

import java.util.ArrayList;

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

        for(Group grp: ownedGroups){
            ownedObsList.add(new Label(grp.getName()));
        }
        ownerList.setItems(ownedObsList);

        //Find groups where user is member
        ArrayList<Group> memberGroups = gd.getGroups(mainApp.getUser(),false);

        ObservableList<Label> memberObsList = FXCollections.observableArrayList();

        for(Group grp: memberGroups){
            memberObsList.add(new Label(grp.getName()));
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
