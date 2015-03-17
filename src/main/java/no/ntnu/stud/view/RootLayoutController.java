package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import no.ntnu.stud.MainApp;


/**
 * Created by Kristian on 25/02/15.
 */
public class RootLayoutController {
    private MainApp mainApp;

    @FXML
    private Label lblStatusBar;

    public RootLayoutController() {
        //trollllooolllll
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

    public void setLblStatusBar(String str){
        lblStatusBar.setText(str);
    }

}
