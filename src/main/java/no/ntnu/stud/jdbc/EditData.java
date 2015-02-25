package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;
import no.ntnu.stud.util.TimeConverter;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.time.LocalDateTime;

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

    public void deleteReservation(int appointmentID){
        Connection con = DBConnector.getCon();

        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "UPDATE appointment SET roomID = NULL WHERE appointmentID = "+appointmentID+";";
                stmt.executeQuery(sql);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print("No Connection");
        }
    }

    public void changeReservationTime(int appointmentID, LocalDateTime newFrom, LocalDateTime newTo){
        Connection con = DBConnector.getCon();
        Timestamp from = TimeConverter.localDateTimeToTimestamp(newFrom);
        Timestamp to = TimeConverter.localDateTimeToTimestamp(newTo);
        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "UPDATE appointment " +
                        "SET from_time = "+from+", to_time = "+to+" " +
                        "WHERE appointmentID = "+appointmentID+";";
                stmt.executeQuery(sql);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print("No Connection");
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
