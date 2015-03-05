package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.User;
import no.ntnu.stud.security.SHAHashGenerator;
import no.ntnu.stud.util.TimeConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by adrianh on 21.02.15.
 */
public class InsertData {

    public static User createUser(String lastName, String middleName, String givenName, String email, String password) {
        Connection con = DBConnector.getCon();
        int userID = 0;
        byte[] salt = SHAHashGenerator.getSalt();
        byte[] hash = SHAHashGenerator.hash(password.toCharArray(), salt);

        if (con != null) {
            String query = "INSERT INTO user ("
                    + "lastName,"
                    + "middleName,"
                    + "givenName,"
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

            String getID = "SELECT userID FROM user WHERE email='" + email + "';";
            try {
                Statement stmt = con.createStatement();
                ResultSet rset = stmt.executeQuery(getID);
                while (rset.next()) {
                    userID = rset.getInt("userID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return GetData.getUser(userID);
    }

    public static void createAppointment(Appointment appointment) {
        Connection con = DBConnector.getCon();

        if (con != null) {
            String query = "INSERT INTO appointment ("
                    + "title,"
                    + "appointmentDate,"
                    + "startTime,"
                    + "endTime,"
                    + "location,"
                    + "roomID,"
                    + "ownerID, "
                    + "attending,"
                    + "alarmTime,"
                    + "description) VALUES ("
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, appointment.getTitle());
                stmt.setString(2, appointment.getDate().toString());
                stmt.setString(3, appointment.getStart().toString());
                stmt.setString(4, appointment.getEnd().toString());
                stmt.setString(5, appointment.getLocation());
                stmt.setInt(6, appointment.getRoomID());
                stmt.setInt(7, appointment.getOwner().getUserID());
                stmt.setInt(8, appointment.getAttending());
                if (appointment.getAlarmTime() != null) {
                    stmt.setTimestamp(9, TimeConverter.localDateTimeToTimestamp(appointment.getAlarmTime()));
                } else {
                    stmt.setTimestamp(9, null);
                }
                stmt.setString(10, appointment.getDescription());
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
                System.out.println("Performing SQL Query [" + query + "]");
                Statement stmt = con.prepareStatement(query);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
    }

    public void setNotification(ArrayList<User> users, String message){
        Connection con = DBConnector.getCon();
        int notificationID;
        if(con != null) try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO notification(message) VALUES('" + message + "')";
            System.out.println("Performing SQL Query [" + sql + "]");
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            String getID = "SELECT LAST_INSERT_ID()";
            ResultSet rs = stmt.executeQuery(getID);
            rs.next();
            notificationID = rs.getInt(1);

            for (User user : users) {
                sql = "INSERT INTO hasNotification(userID, notificationID) VALUES(" + user.getUserID() + ", " + notificationID + ")";
                System.out.println("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        else {
            System.err.print("No Connection");
        }
    }

    public static void main(String[] args) {
        //createUser("Normann", "", "Ola", "ola.normann@mail.no", "passord");
    }
}
