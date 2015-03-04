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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        calendar = Calendar.getInstance();

        //calendar.set(2016,4,1);
        System.out.println(dateFormat.format(calendar.getTime()));
        System.out.println(calendar.get(Calendar.MONTH));
        lblNextYear.setText(""+(calendar.get(Calendar.YEAR)+1));
        lblPrevYear.setText(""+(calendar.get(Calendar.YEAR)-1));
        lblCurrentMonth.setText(TimeConverter.monthToString(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.YEAR));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        renderCalendar(calendar);

    }

    public void renderCalendar(Calendar calendar){
        dates.addAll(Arrays.asList(d00,d01,d02,d03,d04,d05,d06,d10,d11,d12,d13,d14,d15,d16,d20,d21,d22,d23,d24,d25,d26,d30,d31,d32,d33,d34,d35,d36,d40,d41,d42,d43,d44,d45,d46,d50,d51));
        int startOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println(calendar.DAY_OF_WEEK+"som = "+startOfMonth + "motnh : "+ calendar.get(Calendar.MONTH) + "year: " + calendar.get(Calendar.YEAR));
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("flsmakfma: "+calendar.getFirstDayOfWeek());
        System.out.println(totalDays);
        //System.out.println(dates.get(1).getText());
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

    private Date getFirstDateOfCurrentMonth(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }




    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
