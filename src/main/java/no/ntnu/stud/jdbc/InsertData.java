package no.ntnu.stud.jdbc;

import no.ntnu.stud.security.SHAHashGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by adrianh on 21.02.15.
 */
public class InsertData {

    public static void createUser(String lastName, String middleName, String givenName, String password, String email) {
        Connection con = DBConnector.getCon();
        String[] passwordSalt = SHAHashGenerator.getSecurePassword(password);

        if (con != null) {
            String query = "INSERT INTO user ("
                    + "last_name,"
                    + "middle_name,"
                    + "given_name,"
                    + "email,"
                    + "password,"
                    + "salt) VALUES ("
                    + "?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, lastName);
                preparedStmt.setString(2, middleName);
                preparedStmt.setString(3, givenName);
                preparedStmt.setString(4, email);
                preparedStmt.setString(5, passwordSalt[0]);
                preparedStmt.setString(6, passwordSalt[1]);
                preparedStmt.execute();
            } catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String hash = "dcc25152a085333511b179e88aff025dd05f4090cb90e2a8e74d922c980013987d63735b8c0d733a91472c8cd007d23e869bb3b7eb6a0cb86234e8b6bc23b4de";
        String salt = "\u009DF\u009D\u009D\u009D\u009D\u009D\u009D\u009D9\u009D\u0007\u007F\u009D";
        System.out.println(hash.equals(SHAHashGenerator.getSHA512SecureHash("passord", salt)));
    }
}
