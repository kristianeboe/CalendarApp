package no.ntnu.stud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import no.ntnu.stud.view.RootLayoutController;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CalendarApp");

        initRootLayout();

        //showCalendarOverview();

    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

            System.out.println("Location: " + loader.getLocation());
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);


            //Ole does shit he shouldt do
            //This loads the mid and right grid in a terrible way
            rootLayout.setCenter(getGrid());
            rootLayout.setRight(getSideGrid());
            //End of shit ole shouldt do

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Ole does shit he shouldt do
    //these functions should be deleted. Oh lord.
    private GridPane getGrid(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Calendar.fxml"));
            return loader.load();

        }
        catch (IOException e){

        }
        return null;
    }

    private GridPane getSideGrid(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UpcomingEvents.fxml"));
            return loader.load();

        }
        catch (IOException e){

        }
        return null;
    }
    //End of shit ole shouldt do
}
