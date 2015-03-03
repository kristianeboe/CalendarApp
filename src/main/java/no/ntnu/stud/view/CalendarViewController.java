package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.util.TimeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Kristian on 25/02/15.
 */
public class CalendarViewController {
    private MainApp mainApp;
    private Calendar calendar;

    @FXML
    private Label right_arrow, left_arrow, lblCurrentMonth;

    @FXML
    private Label d00,d01,d02,d03,d04,d05,d06,d10,d11,d12,d13,d14,d15,d16,d20,d21,d22,d23,d24,d25,d26,d30,d31,d32,d33,d34,d35,d36,d40,d41,d42,d43,d44,d45,d46,d50,d51;

    private ArrayList<Label> dates = new ArrayList<Label>(Arrays.asList(d00,d01,d02,d03,d04,d05,d06,d10,d11,d12,d13,d14,d15,d16,d20,d21,d22,d23,d24,d25,d26,d30,d31,d32,d33,d34,d35,d36,d40,d41,d42,d43,d44,d45,d46,d50,d51));




    //dele opp i dayViewController og Week-og/eller monthViewController?
    public CalendarViewController() {

    }
    @FXML
    public void initialize(){
        calendar = new GregorianCalendar();
        lblCurrentMonth.setText(TimeConverter.monthToString(calendar.MONTH)+" "+calendar.get(Calendar.YEAR));

    }




    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
