package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.model.User;

import java.io.IOException;


/**
 * Created by Kristian on 25/02/15.
 */
public class RootLayoutController {
    private MainApp mainApp;

    @FXML
    Menu menu;

    public RootLayoutController() {
        //trollllooolllll
    }

    public void generateMenu() {
        User user = mainApp.getUser();
        if (user.isSuperuser()) {
            appendAdminMenus();
        }
    }

    public void appendAdminMenus() {
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/AdminMenu.fxml"));
        menu =  loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("Author: Swag\n" +
                "Website: https://github.com/sklirg/fellesprosjekt-2015");

        alert.showAndWait();
    }

    @FXML
    private void handleAddUser() {
        mainApp.showAddUserDialog();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleSignOut() {
        mainApp.signedOut();
    }

}
