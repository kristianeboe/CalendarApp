package no.ntnu.stud.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.EditData;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.model.Group;
import no.ntnu.stud.model.User;

import java.util.ArrayList;


/**
 * Created by kristoffer on 12.03.15.
 */
public class EditGroupController {

    ObservableList<Label> obsUsers = FXCollections.observableArrayList();

    private int groupID;

    @FXML
    TextField inpName, inpInvite;
    @FXML
    public Label lblTitle;

    private MainApp mainApp;

    @FXML
    public ListView members;
    @FXML
    private ComboBox<String> cmbSearchResults;


    public void renderGroup(int groupID){
        this.groupID = groupID;
        GetData gd = new GetData();
        Group group = gd.getGroup(groupID);
        String name = group.getName();

        inpName.setText(name);
        ArrayList<User> users = gd.getUsersInGroup(groupID);


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
        members.setItems(obsUsers);

    }

    @FXML
    private void searchForUser() {
        cmbSearchResults.getItems().clear();
        GetData gd = new GetData();
        String partOfName = inpInvite.getText();
        ArrayList<User> users;
        ArrayList<Group> groups;
        if (partOfName.length() > 2) {
            groups = gd.searchGroup(partOfName);
            users = gd.searchUser(partOfName);
            String results = "";
            if (users.size() > 0) {
                searchResultsUsers.clear();
                for (User usr : users) {
                    System.out.println(invitedUsers);
                    if (usr.getUserID() != (mainApp.getUser().getUserID())) {
                        searchResultsUsers.add(usr);
                        cmbSearchResults.getItems().add(usr.getFullName());
                    }
                }
                cmbSearchResults.show();
            }
            if (groups.size() > 0) {
                for (Group grp : groups) {
                    cmbSearchResults.getItems().add(grp.getName());
                }
                cmbSearchResults.show();
            }
        }
    }

    ArrayList<User> searchResultsUsers = new ArrayList<>();
    ArrayList<User> invitedUsers = new ArrayList<>();

    @FXML
    private void addInvitedUser() {
        if (cmbSearchResults.getItems().size() > 0) {
            int index = cmbSearchResults.getSelectionModel().getSelectedIndex();
            invitedUsers.add(searchResultsUsers.get(index));
            ObservableList<Label> obsUsersInvited = FXCollections.observableArrayList();
            for (User usr : invitedUsers) {
                Label lbl = new Label();
                lbl.setId("" + usr.getUserID());
                lbl.setText(usr.getFullName());
                obsUsersInvited.add(lbl);
            }
            inpInvite.clear();
            members.setItems(obsUsersInvited);
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    @FXML
    void handleSave() {
        if (lblTitle.getText() == "New Group") {
            Group group = new Group();
            for (User usr : invitedUsers) {
                group.add(usr);
            }
            group.add(mainApp.getUser());
            InsertData.createGroup(inpName.getText(), group);

            mainApp.showGroup(group.getGroupID());
        } else {

            for (User usr : invitedUsers) {
                InsertData.addToGroup(usr, this.groupID);
            }
            EditData.editGroupName(groupID, inpName.getText());
            mainApp.showGroup(groupID);
        }
    }
}
