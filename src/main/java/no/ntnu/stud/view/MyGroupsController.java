package no.ntnu.stud.view;

import javafx.fxml.FXML;
import no.ntnu.stud.MainApp;

/**
 * Created by kristoffer on 11.03.15.
 */
public class MyGroupsController {
    private MainApp mainApp;

    public MyGroupsController(){

    }

    public void renderMyGroups(){

    }


    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
