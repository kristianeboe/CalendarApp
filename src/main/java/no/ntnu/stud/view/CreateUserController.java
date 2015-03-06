package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.model.User;

/**
 * Created by sklirg on 06/03/15.
 */
public class CreateUserController {
    private MainApp mainApp;
    @FXML
    private TextField inpEmail;
    @FXML
    private TextField inpGivenName;
    @FXML
    private TextField inpMiddleName;
    @FXML
    private TextField inpLastName;
    @FXML
    private PasswordField inpPassword;
    @FXML
    private PasswordField inpPassword2;

    @FXML
    private void initialize() {

    }

    /*
    private User createUser() {
        String email = inpEmail.getText();
        String givenName = inpGivenName.getText();
        String middleName = inpMiddleName.getText();
        String lastName = inpLastName.getText();
        String password = inpPassword.getText();
        String password2 = inpPassword2.getText();
        if (!password.equals(password2)) {
            throw new IllegalArgumentException("Passwords does not match");
        }
        //return new User(lastName, middleName, givenName, email);
    }
    */

    @FXML
    private void handleCreateUser() {
        User user;

    }
}
