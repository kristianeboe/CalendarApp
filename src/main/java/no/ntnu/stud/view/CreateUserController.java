package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import no.ntnu.stud.MainApp;
import no.ntnu.stud.model.User;
import org.apache.log4j.Logger;

/**
 * Created by sklirg on 06/03/15.
 */
public class CreateUserController {
    private static Logger logger;

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
        logger = Logger.getLogger("CreateUserCtrl");
    }

    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp;}

    private User createUser() {
        String email = inpEmail.getText();
        String givenName = inpGivenName.getText();
        String middleName = inpMiddleName.getText();
        String lastName = inpLastName.getText();
        String password = inpPassword.getText();
        String password2 = inpPassword2.getText();
        if (!password.equals(password2)) {
            logger.info("Passwords does not match");
            throw new IllegalArgumentException("Passwords does not match");
        }
        return new User(lastName, middleName, givenName, email, password);
    }

    @FXML
    private void handleClose() {
        mainApp.showCalendarView();
    }

    @FXML
    private void handleCreateUser() {
        User user = createUser();
        user.create();
        logger.info("User created");
        mainApp.showCalendarView();
    }
}
