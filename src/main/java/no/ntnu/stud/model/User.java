package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by adrianh on 23.02.15.
 */
public class User {

    private int userID;
    private String lastName;
    private String middleName;
    private String givenName;
    private String email;

    public User(int userID, String lastName, String middleName, String givenName, String email) {
        setUserID(userID);
        setLastName(lastName);
        setMiddleName(middleName);
        setGivenName(givenName);
        setEmail(email);
    }


    public int getUserID() {
        return userID;
    }


    public void setUserID(int userID) {
        this.userID=userID;
    }

    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public String getMiddleName() {
        return middleName;
    }


    public void setMiddleName(String middleName) {
        this.middleName=middleName;
    }

    public String getGivenName() {
        return givenName;
    }


    public void setGivenName(String givenName) {
        this.givenName=givenName;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email=email;
    }
}