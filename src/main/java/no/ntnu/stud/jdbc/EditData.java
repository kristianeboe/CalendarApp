package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Adrian on 23.02.2015.
 */
public class EditData {

    public static void changePassword(User user, String oldPassword, char[] newPassword, byte[] newSalt) throws UnsupportedEncodingException, SQLException {
        Connection con = DBConnector.getCon();

        byte[] hash = SHAHashGenerator.hash(newPassword, newSalt);

        if (Authentication.authenticate(user.getEmail(), oldPassword)) {
            String query = "UPDATE user " +
                    "SET password = ?, salt = ? " +
                    "WHERE userID = ?;";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setBytes(1, hash);
            stmt.setBytes(2, newSalt);
            stmt.setInt(3, user.getUserID());
            stmt.execute();
            System.out.println("Performing SQL Query [" + query + "]");
        }
    }

    public static String forgotPassword(String email) throws SQLException {
        Connection con = DBConnector.getCon();
        String newPassword = SHAHashGenerator.generateRandomPassword(8);
        byte[] newSalt = SHAHashGenerator.getSalt();

        byte[] hash = SHAHashGenerator.hash(newPassword.toCharArray(), newSalt);

        String query = "UPDATE user SET password = ?, salt = ? WHERE email = ?";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setBytes(1, hash);
        stmt.setBytes(2, newSalt);
        stmt.setString(3, email);
        stmt.execute();
        System.out.println("Performing SQL Query [" + query + "]");

        return newPassword;
    }

    public static void deleteUser(int userID) throws SQLException {
        Connection con = DBConnector.getCon();

        if (con != null) {
            String query = "DELETE FROM user WHERE userID = '" + userID + "';";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Performing SQL Query [" + query + "]");
        } else {
            System.err.print("No Connection");
        }
    }

    public void deleteReservation(int appointmentID){
        Connection con = DBConnector.getCon();

        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "UPDATE appointment SET roomID = NULL WHERE appointmentID = "+appointmentID+";";
                System.out.println("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print("No Connection");
        }
    }

    public void changeReservationTime(int appointmentID, LocalTime newStartTime, LocalTime newEndTime, LocalDate newDate){
        Connection con = DBConnector.getCon();
        String startTime = newStartTime.toString();
        String endTime = newEndTime.toString();
        String date = newDate.toString();
        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "UPDATE appointment " +
                        "SET startTime = '"+startTime+"', endTime = '"+endTime+"', appointmentDate = '"+date+"' " +
                        "WHERE appointmentID = "+appointmentID+";";
                System.out.println("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print("No Connection");
        }
    }

    public static void main(String[] args) {

    }
}
