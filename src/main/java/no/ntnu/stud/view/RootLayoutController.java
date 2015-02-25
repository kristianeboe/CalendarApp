package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import no.ntnu.stud.MainApp;


/**
 * Created by Kristian on 25/02/15.
 */
public class RootLayoutController {
    private MainApp mainApp;

    public RootLayoutController(){
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
    private void handleExit() {
        System.exit(0);
    }

}
