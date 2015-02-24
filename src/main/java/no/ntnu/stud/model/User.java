package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by adrianh on 23.02.15.
 */
public class User {

    private IntegerProperty userID = new SimpleIntegerProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty middleName = new SimpleStringProperty();
    private StringProperty givenName = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();

    public User(IntegerProperty userID, StringProperty lastName, StringProperty middleName, StringProperty givenName, StringProperty email) {
        this.userID = userID;
        this.lastName = lastName;
        this.middleName = middleName;
        this.givenName = givenName;
        this.email = email;
    }

    public User(int userID, String lastName, String middleName, String givenName, String email) {
        this.userID.set(userID);
        this.lastName.set(lastName);
        this.middleName.set(middleName);
        this.givenName.set(givenName);
        this.email.set(email);
    }

    public int getUserID() {
        return userID.get();
    }

    public IntegerProperty userIDProperty() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getGivenName() {
        return givenName.get();
    }

    public StringProperty givenNameProperty() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName.set(givenName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}