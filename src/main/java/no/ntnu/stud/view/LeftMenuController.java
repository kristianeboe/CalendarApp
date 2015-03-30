package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import no.ntnu.stud.MainApp;

import java.io.IOException;

/**
 * Created by Kristian on 04/03/15.
 */
public class LeftMenuController {

    private MainApp mainApp;

    public LeftMenuController(){

    }

    @FXML
    void goToMyGroups(){
        mainApp.showMyGroups();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
