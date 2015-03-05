package no.ntnu.stud.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;

/**
 * Created by Kristian on 02/03/15.
 */
public class SignInController {

    MainApp mainApp;

    @FXML
    private TextField inpEmail, inpPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Label lblError;

    public SignInController(){

    }

    @FXML
    private void initialize(){
        lblError.setVisible(false);
        activateFocusedProperties();
    }

    @FXML
    private void handleSignIn(){
        lblError.setVisible(false);
        Authentication authentication = new Authentication();
        User user = authentication.login(inpEmail.getText(), inpPassword.getText());
        if (user != null) {
            mainApp.setUser(user);
            mainApp.signedIn();
        } else {
            lblError.setText("Email or password wrong");
            lblError.setVisible(true);
            inpEmail.getStyleClass().add("errorTextField");
            inpPassword.getStyleClass().add("errorTextField");
            inpEmail.clear();
            inpPassword.clear();

        }

    }

    private void activateFocusedProperties() {
        inpEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                clearStyle(inpEmail);
            }
        });
        inpPassword.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                clearStyle(inpPassword);
            }
        });
    }


    private void clearStyle(TextField textField){
        textField.getStyleClass().remove("errorTextField");
    }

    @FXML
    private void handleSkip(){
        lblError.setVisible(false);
        Authentication authentication = new Authentication();
        User user = authentication.login("test@test.no", "123");
        if (user != null) {
            mainApp.setUser(user);
            mainApp.signedIn();
        } else {
            lblError.setText("Email or password wrong");
            lblError.setVisible(true);
            inpEmail.getStyleClass().add("errorTextField");
            inpPassword.getStyleClass().add("errorTextField");
            inpEmail.clear();
            inpPassword.clear();

        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
