package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(userID < 0){
            throw new IllegalArgumentException("userID cannot be a negative number");
        }else if(userID == 0){
            throw new IllegalArgumentException("userID cannot be 0");
        }
        this.userID=userID;
    }

    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        if(lastName.isEmpty()){
            throw new IllegalArgumentException("lastName cannot be empty");
        }else if(lastName.length()>45){
            throw new IllegalArgumentException("lastName cannot be longer than 45 characters");
        }
        this.lastName=lastName;
    }

    public String getMiddleName() {
        return middleName;
    }


    public void setMiddleName(String middleName) {
        if(middleName.length()>45){
            throw new IllegalArgumentException("middleName cannot be longer than 45 characters");
        }
        this.middleName=middleName;
    }

    public String getGivenName() {
        return givenName;
    }


    public void setGivenName(String givenName) {
        if(givenName.length()>45){
            throw new IllegalArgumentException("givenName cannot be longer than 45 characters");
        }
        this.givenName=givenName;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if(!matcher.matches()){
            throw new IllegalArgumentException("Invalid email-address");
        }

        this.email=email;
    }
}