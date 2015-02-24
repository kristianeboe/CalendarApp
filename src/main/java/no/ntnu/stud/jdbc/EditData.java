package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Adrian on 23.02.2015.
 */
public class EditData {

    public static void changePassword(User user, String oldPassword, char[] newPassword, byte[] newSalt) {
        Connection con = DBConnector.getCon();

        byte[] hash = SHAHashGenerator.hash(newPassword, newSalt);
        String hashString = "";

        if (!Authentication.authenticate(user.getEmail(), oldPassword)) {
            String query = "SELECT password, salt FROM user " +
                    "WHERE userID = '" + user.getUserID() + "';";
            try {
                Statement stmt = con.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(query);
                stmt.execute(query);
                System.out.println("Performing SQL Query [" + query + "]");

                rs.absolute(1);
                rs.updateBytes("password", SHAHashGenerator.hash(newPassword, newSalt));
                rs.updateBytes("salt", newSalt);
                rs.updateRow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        changePassword(GetData.getUser(1), "passord", "banan".toCharArray(), SHAHashGenerator.getSalt());
        System.out.println(Authentication.authenticate(GetData.getUser(1).getEmail(), "12345"));
    }
}
