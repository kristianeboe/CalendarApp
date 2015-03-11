package no.ntnu.stud.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.util.TimeConverter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    boolean mainCalendar;
    private ArrayList<Label> dates = new ArrayList<>();




    //dele opp i dayViewController og Week-og/eller monthViewController?
    public CalendarViewController() {

    }

    public void initialize(){
        mainCalendar = false;
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

    }

    public void renderCalendar(){
        if(mainCalendar){
            lblNextYear.setText("" + (calendar.get(Calendar.YEAR) + 1));
            lblPrevYear.setText(""+(calendar.get(Calendar.YEAR)-1));
        }


        dates.clear();
        dates.addAll(Arrays.asList(d00, d01, d02, d03, d04, d05, d06, d10, d11, d12, d13, d14, d15, d16, d20, d21, d22, d23, d24, d25, d26, d30, d31, d32, d33, d34, d35, d36, d40, d41, d42, d43, d44, d45, d46, d50, d51));
        GetData gd = new GetData();



        //Set Year and month labels
        lblCurrentMonth.setText(TimeConverter.monthToString(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.YEAR));

        // Get first day of month and total days in month
        int startOfMonth = getFirstDayOfMonth(calendar);
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Fill with "" before first day of month
        for(int i = 0; i < startOfMonth;i++){
            dates.get(i).setText("");
            dates.get(i).getStyleClass().remove("dates");
        }

        final EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                Object source = event.getSource();
                Label clickedLabel = (Label) source;
                int day  = Integer.parseInt(clickedLabel.getText().replaceAll("\\s+",""));
                Calendar cal = calendar;
                cal.set(Calendar.DAY_OF_MONTH,day);
                mainApp.showAgenda(cal);
            }
        };

        //Render calendar dates
        int j = 1;
        for(int i = startOfMonth; i < dates.size(); i++){
            if(j>totalDays){
                dates.get(i).setText("");
                dates.get(i).getStyleClass().remove("singleDate");
                dates.get(i).getStyleClass().remove("Date");
            }else{
                if(j<10){
                    dates.get(i).setText((" "+(j)+" "));
                    dates.get(i).getStyleClass().add("singleDate");
                }else{
                    dates.get(i).setText((""+(j)));
                    dates.get(i).getStyleClass().add("dates");
                }

                dates.get(i).getStyleClass().add("dates");
            }
            //Remove underline and blue dates
            dates.get(i).setStyle("-fx-underline: false; -fx-text-fill: black;");

            //Set onClickEvent
            dates.get(i).setOnMouseClicked(clickHandler);

            j++;
        }

        //get all appointments this month
        ArrayList<Appointment> allAppointments = gd.getAppointments(mainApp.getUser(), 5000, false);
        ArrayList<Appointment> appointmentsThisMonth = new ArrayList<>();
        for(int i = 0; i < allAppointments.size();i++){
            LocalDate appointmentDate = allAppointments.get(i).getDate();
            if(appointmentDate.getYear() == calendar.get(Calendar.YEAR) && appointmentDate.getMonthValue() == (calendar.get(Calendar.MONTH)+1)){
                appointmentsThisMonth.add(allAppointments.get(i));
            }
        }

        //Add blue text fill and underline to appointment dates
        for(int k = 0; k < appointmentsThisMonth.size(); k++){
            int dayOfMonth = appointmentsThisMonth.get(k).getDate().getDayOfMonth();
            dates.get(dayOfMonth+startOfMonth-1).setStyle("-fx-underline: true; -fx-text-fill: #3e94ec;");

        }

    }



    private void Handler(Label lblDay, int day){
        Calendar cal = calendar;
        cal.set(Calendar.DAY_OF_MONTH,day);
        lblDay.setOnMouseClicked((event) ->{
            mainApp.showAgenda(cal);
        });
    }

    private int getFirstDayOfMonth(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        String fwd = dateFormat.format((cal.getTime()));
        switch (fwd){
            case "Mon":case "ma": return 0;
            case "Tue":case "ti": return 1;
            case "Wed":case "on": return 2;
            case "Thu":case "to": return 3;
            case "Fri":case "fr": return 4;
            case "Sat":case "lø": return 5;
            case "Sun":case "sø": return 6;
        }
        return -1;
    }

    @FXML
    void nextMonth(){
        calendar.add(Calendar.MONTH,1);
        renderCalendar();
    }

    @FXML
    void previousMonth(){
        calendar.add(Calendar.MONTH,-1);
        renderCalendar();
    }

    @FXML
    void nextYear(){
        calendar.add(Calendar.YEAR,1);
        renderCalendar();
    }

    @FXML
    void previousYear(){
        calendar.add(Calendar.YEAR,-1);
        renderCalendar();
    }



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setMainCalendar(){
        this.mainCalendar = true;
    }
}
