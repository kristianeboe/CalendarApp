package no.ntnu.stud.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import no.ntnu.stud.MainApp;

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
}
