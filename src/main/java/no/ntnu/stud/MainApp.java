package no.ntnu.stud;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.User;
import no.ntnu.stud.view.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private User user;
    private static Logger logger;
    private RootLayoutController rootLayoutController;


    public static void main(String[] args) {
        startLogger();
        logger.info("Welcome to Ultimate Saga Calendar Pro 365 Cloud Edition!");
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

        initRootLayout();

        showSignInView();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            rootLayoutController = loader.getController();
            rootLayoutController.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

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
        if (user.isSuperuser()){
            setAdminMenu();
        } else {
            setDefaultMenu();
        }

        showCalendarView();

        showUpcomingEvents();

        showLeftMenu();

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
            controller.renderViewAppointment(appointment);

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

    public void setDefaultMenu(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DefaultMenu.fxml"));
            MenuBar menuBar =  loader.load();
            rootLayout.setTop(menuBar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAdminMenu(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AdminMenu.fxml"));
            MenuBar menuBar =  loader.load();
            rootLayout.setTop(menuBar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
