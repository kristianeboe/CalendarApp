package no.ntnu.stud.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kristian on 18/03/15.
 */
public class WeekViewController {
    @FXML
    private Label lblDateMon, lblDateTue, lblDateWed, lblDateThu, lblDateFri, lblDateSat, lblDateSun, lblCurrentWeek;
    @FXML
    private GridPane gridWeek;
    @FXML
    private ScrollPane scrollWeek;

    GridPane oldGridWeek;

    private MainApp mainApp;

    private Calendar calendar;

    private ArrayList<Appointment> monday;
    private ArrayList<Appointment> tuesday;
    private ArrayList<Appointment> thirsday;
    private ArrayList<Appointment> wedensday;
    private ArrayList<Appointment> friday;
    private ArrayList<Appointment> saturday;
    private ArrayList<Appointment> sunday;
    private ArrayList<ArrayList<Appointment>> week;


    @FXML
    private void initialize(){
    }

    public WeekViewController(){

    }

    public void renderDates(Calendar calendar) {
        this.calendar = calendar;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");
        lblCurrentWeek.setText("Week: "+calendar.get(Calendar.WEEK_OF_YEAR));
        ArrayList<Label> week = new ArrayList<>(Arrays.asList(lblDateMon, lblDateTue, lblDateWed, lblDateThu, lblDateFri, lblDateSat, lblDateSun));


        Calendar first = (Calendar) calendar.clone();
        first.add(Calendar.DAY_OF_WEEK,
                first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));
        for(Label day:week) {
            day.setText(dateFormat.format(first.getTime()));
            renderAgenda(first.getTime());
            first.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    public void renderAgenda(Date date){
        GetData gd = new GetData();
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyy-MM-dd");
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String dateStr = dbFormat.format(date);
        ArrayList<Appointment> appointments = gd.getAppointments(mainApp.getUser(), dateStr);
        for (Appointment app:appointments) {
            int start = app.getStart().getHour();
            int end = app.getEnd().getHour();
            for (int i = start; i < end; i++) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: #3e94ec");
                gridWeek.add(pane, day, i);
                gridWeek.add(new Label(app.getTitle()), day, i);

            }
        }


    }

    @FXML
    private void handleNextWeek(){
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        renderDates(calendar);
    }

    @FXML
    private void handlePrevWeek(){
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        renderDates(calendar);
    }

    @FXML
    void handleClose(){
        mainApp.showCalendarView();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


}
