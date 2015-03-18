package no.ntnu.stud.model;

import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.jdbc.InsertData;
import no.ntnu.stud.util.InputValidator;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adrianh on 23.02.15.
 */
public class User implements Inevitable {

    private int userID;
    private String lastName, middleName, givenName;
    private String email;
    private byte[] hash, salt;
    private boolean superuser;

    // This should not be allowed for outsiders. This is only used for internal calls. Therefore, private.
    private User(String lastName, String middleName, String givenName, String email) {
        setLastName(lastName);
        setMiddleName(middleName);
        setGivenName(givenName);
        setEmail(email);
        userID = -1;
        superuser = false;
    }

    /**
     * Creates a new user object
     * @param userID User id
     * @param lastName User last name
     * @param middleName User middle name
     * @param givenName User given name
     * @param email User email
     * @param passwordHashMap Map of a user's hashed password and salt
     * @param superuser Flagged if user is superuser
     */
    public User(int userID, String lastName, String middleName, String givenName, String email, HashMap<String, byte[]> passwordHashMap, boolean superuser) {
        this(lastName, middleName, givenName, email, passwordHashMap, superuser);
        setUserID(userID);
    }

    public User(String lastName, String middleName, String givenName, String email, HashMap<String, byte[]> passwordHashMap) {
        this(lastName, middleName, givenName, email);
        setHashSaltByPasswordHashMap(passwordHashMap);
    }

    public User(int userID, String lastName, String middleName, String givenName, String email) {
        this(lastName, middleName, givenName, email);
        setUserID(userID);
    }

    public User(int userID, String lastName, String middleName, String givenName, String email, boolean superuser) {
        this(lastName, middleName, givenName, email);
        setUserID(userID);
        setSuperuser(superuser);
    }

    public User(int userID, String lastName, String middleName, String givenName, String email, HashMap<String, byte[]> passwordHashMap) {
        this(lastName, middleName, givenName, email, passwordHashMap);
        setUserID(userID);
    }

    public User(String lastName, String middleName, String givenName, String email, HashMap<String, byte[]> passwordHashMap, boolean superuser) {
        this(lastName, middleName, givenName, email, passwordHashMap);
        this.superuser = superuser;
    }

    public User(String lastName, String middleName, String givenName, String email, String password) {
        this(lastName, middleName, givenName, email, InsertData.createPasswordHashMap(password));
    }

    public User(String lastName, String middleName, String givenName, String email, byte[] hash, byte[] salt) {
        this(lastName, middleName, givenName, email, InsertData.createPasswordHashMap(hash, salt));
    }

    public User(int userID, String lastName, String middleName, String givenName, String email, byte[] hash, byte[] salt) {
        this(userID, lastName, middleName, givenName, email, InsertData.createPasswordHashMap(hash, salt));
        this.hash = hash;
        this.salt = salt;
    }


    public String toString() {
        return "(<User: " + getUserID() + "> " + getFullName() + ")";
    }

    public int getUserID() {
        return userID;
    }


    public void setUserID(int userID) {
        if(userID < 0){
            throw new IllegalArgumentException("userID cannot be a negative number");
        }
        this.userID=userID;
    }

    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = InputValidator.textInputValidator(lastName,1, 45);
    }

    public String getMiddleName() {
        return middleName;
    }


    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGivenName() {
        return givenName;
    }


    public void setGivenName(String givenName) {
        this.givenName = InputValidator.textInputValidator(givenName, 45);
    }

    public String getFullName() {
        String name = givenName;
        if (middleName.length() > 0) {
            name += " " + middleName;
        }
        name += " " + lastName;
        return name;
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

    public byte[] getHash() {
        return hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    private void setHashSaltByPasswordHashMap(HashMap<String, byte[]> passwordHashMap) {
        this.hash = passwordHashMap.get("hash");
        this.salt = passwordHashMap.get("salt");
    }

    public HashMap<String, byte[]> getPasswordHashMap() {
        HashMap<String, byte[]> passwordHashMap = new HashMap<>();
        passwordHashMap.put("hash", hash);
        passwordHashMap.put("salt", salt);
        return passwordHashMap;
    }

    public boolean isSuperuser() { return superuser; }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }

    public int getId() {
        return this.getUserID();
    }

    public String getName() {
        return this.getFullName();
    }

    public static User getById(int userID) {
        return GetData.getUser(userID);
    }

    public User create() {
        return InsertData.createUser(this);
    }
}