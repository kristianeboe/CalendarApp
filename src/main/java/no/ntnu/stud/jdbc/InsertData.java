package no.ntnu.stud.jdbc;

import no.ntnu.stud.security.SHAHashGenerator;
import no.ntnu.stud.util.TimeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

/**
 * Created by adrianh on 21.02.15.
 */
public class InsertData {

    public static void createUser(String lastName, String middleName, String givenName, String password, String email) {
        Connection con = DBConnector.getCon();
        byte[] salt = SHAHashGenerator.getSalt();
        byte[] hash = SHAHashGenerator.hash(password.toCharArray(), salt);

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
                preparedStmt.setBytes(5, hash);
                preparedStmt.setBytes(6, salt);
                preparedStmt.execute();
                System.out.println("Performing SQL Query [" + query + "]");
            } catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
            }
        }
    }

    public static void createAppointment(String title, LocalDateTime from, LocalDateTime to, int ownerID) {
        Connection con = DBConnector.getCon();


        if (con != null) {
            String query = "INSERT INTO appointment ("
                    + "title,"
                    + "from_time,"
                    + "to_time,"
                    + "ownerID) VALUES ("
                    + "?, ?, ?, ?)";
            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, title);
                stmt.setTimestamp(2, TimeConverter.localDateTimeToTimestamp(from));
                stmt.setTimestamp(3, TimeConverter.localDateTimeToTimestamp(to));
                stmt.setInt(4, ownerID);
                stmt.execute();
                System.out.println("Performing SQL Query [" + query + "]");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void bookRoom(int roomID, int appointmentID) {
        Connection con = DBConnector.getCon();

        if (con != null) {
            String query = "UPDATE appointment SET roomID = '" + roomID + "' WHERE appointmentID = '" + appointmentID + "';";
            try {
                Statement stmt = con.prepareStatement(query);
                stmt.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
    }

    public static void main(String[] args) {
    }
}
