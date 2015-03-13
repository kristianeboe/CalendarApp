package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.User;
import no.ntnu.stud.security.Authentication;
import no.ntnu.stud.security.SHAHashGenerator;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Adrian on 23.02.2015.
 */
public class EditData {
    private static Logger logger = Logger.getLogger("EditData");

    public static void changePassword(User loggedInUser, String oldPassword, char[] newPassword, byte[] newSalt) throws UnsupportedEncodingException, SQLException {
        Connection con = DBConnector.getCon();

        byte[] hash = SHAHashGenerator.hash(newPassword, newSalt);

        if (Authentication.authenticate(loggedInUser.getEmail(), oldPassword)) {
            String query = "UPDATE user " +
                    "SET password = ?, salt = ? " +
                    "WHERE userID = ?;";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setBytes(1, hash);
            stmt.setBytes(2, newSalt);
            stmt.setInt(3, loggedInUser.getUserID());
            stmt.execute();
            logger.debug("Performing SQL Query [" + query + "]");
        } else {
            throw new IllegalArgumentException("Wrong password");
            //System.err.print("Wrong password");
        }
    }

    public static String forgotPassword(String email) {
        Connection con = DBConnector.getCon();
        String newPassword = SHAHashGenerator.generateRandomPassword(8);
        byte[] newSalt = SHAHashGenerator.getSalt();

        byte[] hash = SHAHashGenerator.hash(newPassword.toCharArray(), newSalt);

        String query = "UPDATE user SET password = ?, salt = ? WHERE email = ?";

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(query);
            stmt.setBytes(1, hash);
            stmt.setBytes(2, newSalt);
            stmt.setString(3, email);
            stmt.execute();
            logger.debug("Performing SQL Query [" + query + "]");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Authentication.authenticate(email, newPassword) ? newPassword : null;
    }

    public static void deleteUser(int userID) throws SQLException {
        Connection con = DBConnector.getCon();

        if (con != null) {
            String query = "DELETE FROM user WHERE userID = '" + userID + "';";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            logger.debug("Performing SQL Query [" + query + "]");
        } else {
            logger.fatal("No Connection");
        }
    }

    public static void deleteReservation(int appointmentID){
        Connection con = DBConnector.getCon();

        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "UPDATE appointment SET roomID = NULL WHERE appointmentID = "+appointmentID+";";
                logger.debug("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            logger.fatal("No Connection");
        }
    }

    public static void changeReservationTime(int appointmentID, LocalTime newStartTime, LocalTime newEndTime, LocalDate newDate){
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
                logger.debug("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            logger.fatal("No Connection");
        }
    }

    public static void acceptInvitation(User user, Appointment appointment){
        Connection con = DBConnector.getCon();
        int userID = user.getUserID();
        int appointmentID = appointment.getAppointmentID();

        if(con!=null){
            try{
                Statement stmt = con.createStatement();
                String sql = "UPDATE userInvited" +
                        " SET attending = '1' " +
                        "WHERE userID = "+userID+" AND appointmentID = "+appointmentID+";";
                logger.debug("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }else{
            logger.fatal("No Connection");
        }
    }

    public static void declineInvitation(User user, Appointment appointment){
        Connection con = DBConnector.getCon();
        int userID = user.getUserID();
        int appointmentID = appointment.getAppointmentID();

        if(con!=null){
            try{
                Statement stmt = con.createStatement();
                String sql = "UPDATE userInvited" +
                        " SET attending = '2' " +
                        "WHERE userID = "+userID+" AND appointmentID = "+appointmentID+";";
                logger.debug("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }else{
            logger.fatal("No Connection");
        }
    }

    public static void editGroupName(int groupID, String newName) {
        Connection con = DBConnector.getCon();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String sql = "UPDATE userGroup" +
                        " SET name = '" + newName + "' " +
                        "WHERE groupID = " + groupID + ";";
                logger.debug("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No Connection");
        }
    }

    public static Appointment editAppointment(Appointment a) {
        Connection con = DBConnector.getCon();
        Appointment edited_appointment = null;

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String sql = "" +
                        "UPDATE appointment SET " +
                        "title='" + a.getTitle() + "' " +
                        "ownerID='" + a.getOwner() + "' " +
                        "startTime='" + a.getStart().toString() + "' " +
                        "endTime='" + a.getEnd().toString() + "' " +
                        "roomID='" + a.getRoomID() + "' " +
                        "location='" + a.getLocation() + "' " +
                        "description='" + a.getDescription() + "' " +
                        "appointmentDate='" + a.getDate().toString() + "' " +
                        "WHERE AppointmentID='" + a.getAppointmentID() + "';";
                logger.debug("Updating " + a + " using [" + sql + "]");
                stmt.execute(sql);
                String getID = "SELECT LAST_INSERT_ID()";
                ResultSet rs = stmt.executeQuery(getID);
                rs.next();
                edited_appointment = a;
                edited_appointment.setAppointmentID(rs.getInt(0));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return edited_appointment;
    }

    public static void main(String[] args) {

    }

}
