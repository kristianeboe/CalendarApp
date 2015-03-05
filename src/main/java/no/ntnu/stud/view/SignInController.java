package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import no.ntnu.stud.MainApp;

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
    private void handleSignIn(){
        if (inpEmail.getText().equals("person@company.com") && inpPassword.getText().equals("swag")){
            mainApp.signedIn();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
