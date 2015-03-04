package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.util.TimeConverter;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kristian on 25/02/15.
 */
public class CalendarViewController {
    private MainApp mainApp;
    private Calendar calendar;

    @FXML
    private Label right_arrow, left_arrow, lblCurrentMonth, lblPrevYear, lblNextYear;

    @FXML
    private Label d00,d01,d02,d03,d04,d05,d06,d10,d11,d12,d13,d14,d15,d16,d20,d21,d22,d23,d24,d25,d26,d30,d31,d32,d33,d34,d35,d36,d40,d41,d42,d43,d44,d45,d46,d50,d51;

    private ArrayList<Label> dates = new ArrayList<>();




    //dele opp i dayViewController og Week-og/eller monthViewController?
    public CalendarViewController() {

    }

    public void initialize(){

        calendar = Calendar.getInstance();
        calendar.set(2015,3,1);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        renderCalendar(calendar);

    }

    public void renderCalendar(Calendar calendar){
        dates.clear();
        dates.addAll(Arrays.asList(d00, d01, d02, d03, d04, d05, d06, d10, d11, d12, d13, d14, d15, d16, d20, d21, d22, d23, d24, d25, d26, d30, d31, d32, d33, d34, d35, d36, d40, d41, d42, d43, d44, d45, d46, d50, d51));

        //Set Year and month labels
        lblNextYear.setText("" + (calendar.get(Calendar.YEAR) + 1));
        lblPrevYear.setText(""+(calendar.get(Calendar.YEAR)-1));
        lblCurrentMonth.setText(TimeConverter.monthToString(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.YEAR));

        // Get first day of month and total days in month
        int startOfMonth = getFirstDayOfMonth(calendar);
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Fill with "-" before first day of month
        for(int i = 0; i < startOfMonth;i++){
            dates.get(i).setText("-");
            dates.get(i).getStyleClass().remove("dates");
        }

        //Render calendar dates
        int j = 1;
        for(int i = startOfMonth; i < dates.size(); i++){
            if(j>totalDays){
                dates.get(i).setText("");
                dates.get(i).getStyleClass().remove("dates");
            }else{
                dates.get(i).setText((""+(j++)));
                dates.get(i).getStyleClass().add("dates");
            }
        }
    }

    private int getFirstDayOfMonth(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        String fwk = dateFormat.format((calendar.getTime()));
        System.out.println("First day of month: "+fwk);
        switch (fwk){
            case "Mon": return 0;
            case "Tue": return 1;
            case "Wed": return 2;
            case "Thu": return 3;
            case "Fri": return 4;
            case "Sat": return 5;
            case "Sun": return 6;
        }
        return -1;
    }

    @FXML
    void nextMonth(){
        calendar.add(Calendar.MONTH,1);
        renderCalendar(calendar);
    }

    @FXML
    void previousMonth(){
        calendar.add(Calendar.MONTH,-1);
        renderCalendar(calendar);
    }



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
