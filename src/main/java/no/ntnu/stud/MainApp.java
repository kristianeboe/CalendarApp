package no.ntnu.stud;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Alarm;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.User;
import no.ntnu.stud.view.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private User user;
    private static Logger logger;
    private ArrayList<Alarm> alarms;
    private RootLayoutController rootLayoutController;

    public static void main(String[] args) {
        startLogger();
        logger.info("Hello World!");


        logger.debug("Starting application");

        launch(args);
    }

    private static void startLogger() {
        //String log4jConfPath = "/resources/no/ntnu/stud/log4j.properties";
        //PropertyConfigurator.configure(log4jConfPath);
        BasicConfigurator.configure();
        logger = Logger.getLogger("Main");
        logger.setLevel(Level.ALL);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Ultimate Saga Calendar Pro 365 Cloud Edition");
        this.primaryStage.getIcons().add(new Image("file:/images/favicon.png"));
        logger.trace("initRootLayout");
        initRootLayout();
        logger.trace("showSignInView");
        showSignInView();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            rootLayoutController = loader.getController();
            rootLayoutController.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSignInView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SignIn.fxml"));
            GridPane signInView = (GridPane) loader.load();
            rootLayout.getStyleClass().add("backgroundColor");
            rootLayout.setCenter(signInView);

            SignInController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signedIn(){
        logger.debug("Signed in successfully");
        logger.info("Logged in as " + user.getFullName());
        showCalendarView();

        showUpcomingEvents();

        showLeftMenu();

        this.alarms = GetData.getAlarms();
    }

    public void signedOut() {
        this.user = null;
        rootLayout.setLeft(null);
        rootLayout.setRight(null);
        showSignInView();
    }

    public void showCalendarView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Calendar.fxml"));
            GridPane calendarView = (GridPane) loader.load();

            rootLayout.setCenter(calendarView);

            CalendarViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setMainCalendar();
            controller.renderCalendar();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWeekView(Calendar calendar){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/WeekView.fxml"));
            GridPane weekView = (GridPane) loader.load();

            rootLayout.setCenter(weekView);

            WeekViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderDates(calendar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUpcomingEvents(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UpcomingEvents.fxml"));
            GridPane upcomingEvents = (GridPane) loader.load();

            rootLayout.setRight(upcomingEvents);

            UpcomingEventsController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderUpcomingAppointments();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLeftMenu(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LeftMenu.fxml"));
            GridPane leftMenu = (GridPane) loader.load();

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(MainApp.class.getResource("view/CalendarSmall.fxml"));
            GridPane smallCal = (GridPane) loader2.load();
            CalendarViewController controller2 = loader2.getController();
            controller2.setMainApp(this);
            controller2.renderCalendar();
            leftMenu.add(smallCal,0,0);

            rootLayout.setLeft(leftMenu);

            LeftMenuController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAppointmentDialog(Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/newAppointment.fxml"));
            GridPane page = (GridPane) loader.load();

            rootLayout.setCenter(page);

            NewAppointmentController controller = loader.getController();
            controller.setMainApp(this);
            if (appointment != null){
                controller.insertAppointmentData(appointment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddUserDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CreateUser.fxml"));
            GridPane page = (GridPane) loader.load();

            rootLayout.setCenter(page);

            CreateUserController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAppointmentView(Appointment appointment){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ViewAppointment.fxml"));
            GridPane viewAppointment = (GridPane) loader.load();
            rootLayout.setCenter(viewAppointment);

            ViewAppointmentController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderAppointment(appointment);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAppointmentView(int appointmentID){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ViewAppointment.fxml"));
            GridPane viewAppointment = (GridPane) loader.load();
            rootLayout.setCenter(viewAppointment);

            ViewAppointmentController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderViewAppointment(appointmentID);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAgenda(Calendar calendar){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Agenda.fxml"));
            GridPane viewAppointment = (GridPane) loader.load();
            rootLayout.setCenter(viewAppointment);

            AgendaController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderAgenda(calendar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMyGroups(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MyGroups.fxml"));
            GridPane viewAppointment = (GridPane) loader.load();
            rootLayout.setCenter(viewAppointment);

            MyGroupsController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderMyGroups();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEditGroup(int groupID){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EditGroup.fxml"));
            GridPane editGroup = (GridPane) loader.load();
            rootLayout.setCenter(editGroup);

            EditGroupController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderGroup(groupID);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewGroup(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EditGroup.fxml"));
            GridPane editGroup = (GridPane) loader.load();
            rootLayout.setCenter(editGroup);

            EditGroupController controller = loader.getController();
            controller.setMainApp(this);
            controller.lblTitle.setText("New Group");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUser(int userID){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/User.fxml"));
            GridPane userView = (GridPane) loader.load();
            rootLayout.setCenter(userView);

            UserController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderUser(userID);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGroup(int groupID){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Group.fxml"));
            GridPane groupView = (GridPane) loader.load();
            rootLayout.setCenter(groupView);

            GroupViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.renderGroup(groupID);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeStatusBar(String message){
            rootLayoutController.setLblStatusBar(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Alarm> getAlarms() {
        return this.alarms;
    }

    public List<Alarm> getAlarmsByUser(int userID) {
        return alarms.stream()
                .filter(a -> getUser().getUserID() == userID)
                .collect(Collectors.toList());
    }

    public List<Alarm> getAlarmsByAppointment(int appointmentID) {
        return alarms.stream()
                .filter(a -> a.getAppointment().getAppointmentID() == appointmentID)
                .collect(Collectors.toList());
    }

    public List<Alarm> getAlarms(int userID, int appointmenID) {
        return alarms.stream()
                .filter(a -> a.getAppointment().getAppointmentID() == appointmenID && a.getUser().getUserID() == userID)
                .collect(Collectors.toList());
    }

    public void addAlarm(Alarm alarm) {
        this.alarms.add(alarm);
    }
}
