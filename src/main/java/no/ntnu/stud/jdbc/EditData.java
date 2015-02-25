package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Adrian on 23.02.2015.
 */
public class EditData {

    public static void changePassword(User user, String oldPassword, char[] newPassword, byte[] newSalt) throws UnsupportedEncodingException {
        Connection con = DBConnector.getCon();

        byte[] hash = SHAHashGenerator.hash(newPassword, newSalt);
        String hashString = new String(hash, "ascii");
        String saltString = new String(newSalt, "ascii");


        if (!Authentication.authenticate(user.getEmail(), oldPassword)) {
            String query = "UPDATE user " +
                    "SET password = '" + hashString + "', salt = '" + saltString + "' " +
                    "WHERE userID = '" + user.getUserID() + "';";
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                System.out.println("Performing SQL Query [" + query + "]");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            changePassword(GetData.getUser(1), "passord", "banan".toCharArray(), SHAHashGenerator.getSalt());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(Authentication.authenticate(GetData.getUser(1).getEmail(), "12345"));
    }
}
