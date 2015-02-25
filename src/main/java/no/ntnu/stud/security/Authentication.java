package no.ntnu.stud.security;

import no.ntnu.stud.jdbc.DBConnector;
import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by adrianh on 23.02.15.
 */
public class Authentication {

    private User loggedInUser = null;

    public User getLoggedInUser() {
        return loggedInUser;
    }

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

    public void logout() {
        loggedInUser = null;
    }

    public static void main(String[] args) {
        Authentication auth = new Authentication();
    }
}
