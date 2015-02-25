package no.ntnu.stud.security;

import no.ntnu.stud.jdbc.DBConnector;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class responsible for logging in, logging out, and authenticating the user.
 * For login, an <code>Authentication</code> object has to be created.
 * The rest of the function are static.
 *
 * @author Adrian Hundseth
 */
public class Authentication {

    /**
     * The user that is currently authenticated. Is null if no user is logged in.
     */
    private User loggedInUser = null;

    /**
     * Returns the logged in user.
     * @return A <code>User</code> object containing the logged in user.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * A simple function to see if a user is logged in.
     * @return Returns true if a user is currently logged in.
     */
    public boolean isLoggedIn() {
        return getLoggedInUser() != null;
    }

    /**
     * Authenticates the user with the provided email and password.
     * @param email <code>String</code> containing the users email.
     * @param password <code>String</code> containing the users password.
     * @return Returns true if authentication was successful, false otherwise
     */
    public static boolean authenticate(String email, String password) {
        Connection con = DBConnector.getCon();
        byte[] hash = null;
        byte[] salt = null;

        String query = "SELECT password, salt FROM user "
                + "WHERE email = '" + email + "'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            System.out.println("Executing SQL query: [" + query + "]");
            while (rset.next()) {
                hash = rset.getBytes("password");
                salt = rset.getBytes("salt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hash != null && salt != null && SHAHashGenerator.isValid(password.toCharArray(), salt, hash);
    }

    /**
     * Sends the email and password to the <code>authenticate</code> function,
     * then returns the logged in user if successful.
     * @param email The users email
     * @param password The users password
     * @return A <code>User</code> object of the currently logged in user. Returns null if authentication failed.
     */
    public User login(String email, String password) {
        loggedInUser = null;

        if (authenticate(email, password)) {
            loggedInUser = GetData.getUser(email);
            System.out.println("Authentication successful");
        } else {
            System.err.println("Authentication failed");
        }

        return loggedInUser;
    }

    /**
     * Logs out the currently logged in user by setting the <code>loggedInUser</code> object to null.
     */
    public void logout() {
        loggedInUser = null;
    }

    public static void main(String[] args) {
        Authentication auth = new Authentication();
    }
}
