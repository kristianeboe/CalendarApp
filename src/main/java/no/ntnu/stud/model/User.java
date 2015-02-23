package no.ntnu.stud.model;

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
        this.userID = userID;
        this.lastName = lastName;
        this.middleName = middleName;
        this.givenName = givenName;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getEmail() {
        return email;
    }
}
