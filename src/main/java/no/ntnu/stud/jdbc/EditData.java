package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Adrian on 23.02.2015.
 */
public class EditData {

    public static void changePassword(User user, String oldPassword, char[] newPassword, byte[] newSalt) throws UnsupportedEncodingException {
        Connection con = DBConnector.getCon();

        byte[] hash = SHAHashGenerator.hash(newPassword, newSalt);

        if (!Authentication.authenticate(user.getEmail(), oldPassword)) {
            String query = "UPDATE user " +
                    "SET password = ?, salt = ? " +
                    "WHERE userID = ?;";
            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setBytes(1, hash);
                stmt.setBytes(2, newSalt);
                stmt.setInt(3, user.getUserID());
                stmt.execute();
                System.out.println("Performing SQL Query [" + query + "]");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
    }
}
