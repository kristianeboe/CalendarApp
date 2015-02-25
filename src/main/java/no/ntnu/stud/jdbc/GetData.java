package no.ntnu.stud.jdbc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import no.ntnu.stud.model.Notification;
import no.ntnu.stud.model.Room;
import no.ntnu.stud.model.User;
import no.ntnu.stud.util.TimeConverter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class for getting data from the database.
 * @author Adrian Hundseth
 */
public class GetData {

    /**
     * Fetches and returns the user with the matching userID
     * @param userID <code>int</code> containing a userID
     * @return A <code>User</code> object matching the userID, null if the user is not found.
     */
    public static User getUser(int userID) {
        Connection con = DBConnector.getCon();
        User user = null;
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user WHERE userID='" + userID + "';";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    String lastName = rset.getString("last_name");
                    String middleName = rset.getString("middle_name");
                    String givenName = rset.getString("given_name");
                    String email = rset.getString("email");
                    user = new User(new SimpleIntegerProperty(userID), new SimpleStringProperty(lastName), new SimpleStringProperty(middleName), new SimpleStringProperty(givenName), new SimpleStringProperty(email));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No Connection");
        }
        return user;
    }

    /**
     * Fetches and returns the user with the matching email
     * @param email <code>String</code> containing an email address
     * @return A <code>User</code> object matching the email, null if the user is not found.
     */
    public static User getUser(String email) {
        Connection con = DBConnector.getCon();
        User user = null;
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user WHERE email='" + email + "';";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    int userID = rset.getInt("userID");
                    String lastName = rset.getString("last_name");
                    String middleName = rset.getString("middle_name");
                    String givenName = rset.getString("given_name");
                    user = new User(userID, lastName, middleName, givenName, email);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No Connection");
        }
        return user;
    }
    
    public static ArrayList<User> getUsers() {
        Connection con = DBConnector.getCon();
        ArrayList<User> users = new ArrayList<User>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    int userID = rset.getInt("userID");
                    String lastName = rset.getString("last_name");
                    String middleName = rset.getString("middle_name");
                    String givenName = rset.getString("given_name");
                    String email = rset.getString("email");
                    User user = new User(new SimpleIntegerProperty(userID), new SimpleStringProperty(lastName), new SimpleStringProperty(middleName), new SimpleStringProperty(givenName), new SimpleStringProperty(email));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No connection");
        }
        return users;
    }

    public static Room getRoomStatus(int roomID, LocalDateTime from, LocalDateTime to) {
        Connection con = DBConnector.getCon();
        Room room = null;

        Timestamp from_time = TimeConverter.localDateTimeToTimestamp(from);
        Timestamp to_time = TimeConverter.localDateTimeToTimestamp(to);

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM room " +
                        "NATURAL JOIN appointment " +
                        "WHERE room.roomID="+roomID+" " +
                        "AND (("+from_time+" > from_time "+
                        "AND "+from_time+" <  to_time) " +
                        "OR ("+to_time+"> from_time "+
                        "AND "+to_time+"< to_time) "+
                        "OR ("+from_time+" < from_time "+
                        "AND "+to_time+" >  to_time ));";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    int returnRoomID = rset.getInt("roomID");
                    String name = rset.getString("name");
                    int capacity = rset.getInt("capacity");
                    room = new Room(returnRoomID, name, capacity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return room;
    }

    /**Gets the smallest available room
     *
     * @return roomID
     */
    public Room getSmallestRoom(LocalDateTime startTime, LocalDateTime endTime, int numPeople){
        Room room = null;
        Connection con = DBConnector.getCon();
        Timestamp startTimestamp = TimeConverter.localDateTimeToTimestamp(startTime);
        Timestamp endTimestamp = TimeConverter.localDateTimeToTimestamp(endTime);
        if(con != null){
            try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM room " +
                        "WHERE capacity >="+numPeople+" " +
                        "AND roomID NOT IN( " +
                        "SELECT roomID FROM appointment " +
                        "WHERE ( ("+startTimestamp+" > from_time " +
                        "AND "+startTimestamp+" < to_time) " +
                        "OR ("+endTimestamp+" > from_time " +
                        "AND "+endTimestamp+" <to_time) " +
                        "OR ("+startTimestamp+" < from_time " +
                        "AND "+endTimestamp+" > to_time) ) ) " +
                        "ORDER BY capacity ASC LIMIT 1;";
                ResultSet rs = stmt.executeQuery(sql);
                int roomID = rs.getInt("roomID");
                String roomName = rs.getString("name");
                int roomCapacity = rs.getInt("capacity");
                room = new Room(roomID, roomName, roomCapacity);
            }catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return room;
    }

    public ArrayList<Notification> getNotifications(int userID){
        Connection con = DBConnector.getCon();
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "SELECT notificationID, message FROM notification NATURAL JOIN hasNotification WHERE userID="+userID+"";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    int notificationID = rs.getInt("notificationID");
                    String message = rs.getString("message");
                    notifications.add(new Notification(notificationID,message));
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print("No Connection");
        }
        return notifications;
    }


}