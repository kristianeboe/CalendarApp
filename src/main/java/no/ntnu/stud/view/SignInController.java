package no.ntnu.stud.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import no.ntnu.stud.MainApp;
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

    public SignInController(){

    }

    @FXML
    private void initialize(){
        activateFocusedProperties();
    }

    @FXML
    private void handleSignIn(){
        Authentication authentication = new Authentication();
        try{
            mainApp.setUser(authentication.login(inpEmail.getText(), inpPassword.getText()));
            mainApp.signedIn();
        } catch (IllegalArgumentException e){
            inpEmail.getStyleClass().add("errorTextField");
            inpPassword.getStyleClass().add("errorTextField");
            inpEmail.clear();
            inpPassword.clear();
            inpEmail.setPromptText(e.getMessage());
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
        mainApp.signedIn();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
