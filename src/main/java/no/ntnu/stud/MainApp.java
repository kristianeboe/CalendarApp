package no.ntnu.stud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.User;
import no.ntnu.stud.view.*;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private User user;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        launch(args);
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

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

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
        showCalendarView();

        showUpcomingEvents();

        showLeftMenu();
    }

    public void showCalendarView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Calendar.fxml"));
            GridPane calendarView = (GridPane) loader.load();

            rootLayout.setCenter(calendarView);

            CalendarViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUpcomingEvents(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UpcomingEvents.fxml"));
            GridPane upcomingEvetns = (GridPane) loader.load();

            rootLayout.setRight(upcomingEvetns);

            UpcomingEventsController controller = loader.getController();
            controller.setMainApp(this);

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
