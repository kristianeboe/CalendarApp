package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import no.ntnu.stud.MainApp;

/**
 * Created by Kristian on 25/02/15.
 */
public class CalendarViewController {
    private MainApp mainApp;

    @FXML
    private Text right_arrow;

    @FXML
    private Text left_arrow;


    //dele opp i dayViewController og Week-og/eller monthViewController?
    public CalendarViewController() {

    }



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
