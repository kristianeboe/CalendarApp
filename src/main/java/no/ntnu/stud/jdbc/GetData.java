package no.ntnu.stud.jdbc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import no.ntnu.stud.model.Room;
import no.ntnu.stud.model.User;
import no.ntnu.stud.util.TimeConverter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by adrianh on 21.02.15.
 */
public class GetData {

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

    public static User getUser(String email) {
        Connection con = DBConnector.getCon();
        User user = null;
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user WHERE userID='" + email + "';";
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
                        "WHERE room.roomID='roomID' " +
                        "AND (('fromDateTime' > " + from_time +
                        "AND 'fromDateTime' < " + to_time +
                        "OR ('toDateTime'> " + from_time +
                        "AND 'toDateTime'< " + to_time +
                        "OR ('fromDateTime' < " + from_time +
                        "AND 'toDateTime' > " + to_time + ");";
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
                String sql = "SELECT * FROM room WHERE capacity >="+numPeople+" AND roomID NOT IN( SELECT roomID FROM appointment WHERE ( ("+startTimestamp+" > from_time AND "+startTimestamp+" < to_time) OR ("+endTimestamp+" > from_time AND "+endTimestamp+" <to_time) OR ("+startTimestamp+" < from_time AND "+endTimestamp+" > to_time) ) ) ORDER BY capacity ASC LIMIT 1;";
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
}