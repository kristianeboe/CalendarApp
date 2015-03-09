package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.*;
import no.ntnu.stud.util.ResultResolver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

                user = ResultResolver.user(rset);

                con.close();
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

                user = ResultResolver.user(rset);

                con.close();
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
        ArrayList<User> users = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                users = ResultResolver.groupResolver(rset);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No connection");
        }
        return users;
    }

    /**
     * TODO: Test for this
     * @param groupID
     * @return A <code>Group</code> object containting all users in the supplied group
     */
    public static Group getGroup(int groupID) {
        Connection con = DBConnector.getCon();
        Group users = new Group();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user NATURAL JOIN userInGroup WHERE groupID = "+groupID+";";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                users = ResultResolver.groupResolver(rset);

                String query = "SELECT * FROM userGroup WHERE groupID = " + groupID + ";";
                rset = stmt.executeQuery(query);
                System.out.println("Performing SQL Query [" + strSelect + "]");
                while (rset.next()) {
                    users.setGroupID(rset.getInt("groupID"));
                    users.setName(rset.getString("name"));
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No connection");
        }
        return users;
    }

    public static Appointment getAppointment(int appointmentID) {
        Connection con = DBConnector.getCon();
        Appointment appointment = null;

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM appointment " +
                        "WHERE appointmentID = " + appointmentID + ";";
                ResultSet rset = stmt.executeQuery(strSelect);
                System.out.println("Performing SQL Query [" + strSelect + "]");

                appointment = ResultResolver.appointmentResolver(rset).get(0);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return appointment;
    }

    public static Appointment getAppointment(int qRoomID, LocalDate qDate, LocalTime qStartTime, LocalTime qEndTime) {
        Connection con = DBConnector.getCon();
        Appointment appointment = null;

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM appointment " +
                        "WHERE roomID = '" + qRoomID + "' " +
                        "AND appointmentDate = '" + qDate + "' " +
                        "AND startTime = '" + qStartTime + "' " +
                        "AND endTime = '" + qEndTime + "'" +
                        ";";
                ResultSet rset = stmt.executeQuery(strSelect);
                System.out.println("Performing SQL Query [" + strSelect + "]");
                appointment = ResultResolver.appointmentResolver(rset).get(0);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return appointment;
    }

    public static ArrayList<Appointment> getAppointments(User user, int limit){
        int userID = user.getUserID();
        Connection con = DBConnector.getCon();
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM userInvited NATURAL JOIN user JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE userID = "+userID+" ORDER BY appointmentDate, startTime ASC LIMIT "+limit+";";
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("Performing SQL Query [" + sql + "]");

                while(rs.next()){
                    int ID = rs.getInt("appointmentID");
                    String title = rs.getString("title");
                    int ownerID = rs.getInt("ownerID");
                    LocalDate date = rs.getTimestamp("appointmentDate").toLocalDateTime().toLocalDate();
                    LocalTime from = rs.getTimestamp("startTime").toLocalDateTime().toLocalTime();
                    LocalTime to = rs.getTimestamp("endTime").toLocalDateTime().toLocalTime();
                    String location = rs.getString("location");
                    if(location == null) location = "";
                    int roomID = rs.getInt("roomID");
                    String description = rs.getString("description");
                    int attending = rs.getInt("attending");
                    LocalDateTime alarmTime = LocalDateTime.parse("0001-01-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    if(rs.getTimestamp("alarmTime") != null){
                        alarmTime = rs.getTimestamp("alarmTime").toLocalDateTime();
                    }
                    //System.out.println("id: "+ID+", ownerID: "+ownerID+", date: "+date.toString()+", from: " +from.toString()+", to: "+to.toString()+", location: "+location+", roomID: "+roomID+", description: "+description+", attening: "+attending+", alarmTime: "+alarmTime.toString());

                    Appointment appointment = new Appointment(ID, title, date, from, to, user, description, location, roomID, attending, alarmTime);
                    appointments.add(appointment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return appointments;
    }

    /**
     *
     *
     * @return True if room is available
     */
    public static boolean roomIsAvailable(int roomID, LocalTime startTime, LocalTime endTime, LocalDate date) {
        Connection con = DBConnector.getCon();
        String from_time = startTime.toString();
        String to_time = endTime.toString();
        String dt = date.toString();
        int counter = 0;

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM room " +
                        "NATURAL JOIN appointment " +
                        "WHERE room.roomID="+roomID+" " +
                        "AND (('"+from_time+"' > startTime "+
                        "AND '"+from_time+"' <  endTime) " +
                        "OR ('"+to_time+"'> startTime "+
                        "AND '"+to_time+"'< endTime) "+
                        "OR ('"+from_time+"' < startTime "+
                        "AND '"+to_time+"' >  endTime ))" +
                        "AND '"+dt+"' = appointmentDate;";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    counter++;
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return counter == 0;
    }

    /**Gets the smallest available room
     *
     * @return roomID
     */
    public static Room getSmallestRoom(LocalTime startTime, LocalTime endTime, LocalDate date, int numPeople){
        Room room = null;
        Connection con = DBConnector.getCon();
        String start =startTime.toString();
        String end = endTime.toString();
        String dt = date.toString();
        if(con != null){
            try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM room " +
                        "WHERE capacity >="+numPeople+" " +
                        "AND roomID NOT IN( " +
                        "SELECT roomID FROM appointment " +
                        "WHERE ( ('"+start+"' > startTime " +
                        "AND '"+start+"' < endTime) " +
                        "OR ('"+end+"' > startTime " +
                        "AND '"+end+"' < endTime) " +
                        "OR ('"+start+"' < startTime " +
                        "AND '"+end+"' > endTime) )" +
                        "AND '"+dt+"' = appointmentDate ) " +
                        "ORDER BY capacity ASC LIMIT 1;";
                System.out.println("Performing SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);
                int roomID = 0;
                String roomName ="";
                int roomCapacity=0;
                while(rs.next()){
                    roomID = rs.getInt("roomID");
                    roomName = rs.getString("name");
                    roomCapacity = rs.getInt("capacity");
                }
                if(roomID <1){
                    System.err.println("No room is available");
                    return null;
                }
                room = new Room(roomID, roomName, roomCapacity);
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return room;
    }

    public static ArrayList<Room> getAllAvailableRooms(LocalTime startTime, LocalTime endTime, LocalDate date, int numPeople){
        ArrayList<Room> rooms = new ArrayList<>();
        // ObservableList<Room> rooms2 = FXCollections.observableArrayList();
        Connection con = DBConnector.getCon();
        String start =startTime.toString();
        String end = endTime.toString();
        String dt = date.toString();
        if(con != null){
            try{
                Statement stmt = con.createStatement();
                String sql ="SELECT * FROM room " +
                        "WHERE capacity >="+numPeople+" "+
                        "AND roomID NOT IN( " +
                        "SELECT roomID FROM appointment " +
                        "WHERE ( ('"+start+"' > startTime " +
                        "AND '"+start+"' < endTime) " +
                        "OR ('"+end+"' > startTime " +
                        "AND '"+end+"' < endTime) " +
                        "OR ('"+start+"' < startTime " +
                        "AND '"+end+"' > endTime) )" +
                        "AND '"+dt+"' = appointmentDate ) " +
                        "ORDER BY capacity ASC;";
                System.out.println("Performing SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    int roomID = rs.getInt("roomID");
                    String roomName = rs.getString("name");
                    int roomCapacity = rs.getInt("capacity");
                    rooms.add(new Room(roomID, roomName, roomCapacity));
                    // rooms2.add(new Room(roomID, roomName, roomCapacity));
                }
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return rooms;
    }

    public static ArrayList<Notification> getNotifications(int userID){
        Connection con = DBConnector.getCon();
        ArrayList<Notification> notifications = new ArrayList<>();
        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "SELECT notificationID, message FROM notification NATURAL JOIN hasNotification WHERE userID="+userID+"";
                System.out.println("Performing SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    int notificationID = rs.getInt("notificationID");
                    String message = rs.getString("message");
                    notifications.add(new Notification(notificationID,message));
                }
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print("No Connection");
        }
        return notifications;
    }

    /**
     * TODO: Create test for this
     * @param userID
     * @return
     */
    public static ArrayList<Appointment> getOwnedAppointments(int userID) {
        Connection con = DBConnector.getCon();
        ArrayList<Appointment> appointments = new ArrayList<>();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM appointment " +
                        "WHERE ownerID = " + userID +
                        " ORDER BY appointmentDate;";
                System.out.println("Peforming SQL Query [" + query + "]");
                ResultSet rs = stmt.executeQuery(query);
                appointments = ResultResolver.appointmentResolver(rs);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
        return appointments;
    }

    /**
     * TODO:Create tests for this
     * @param userID
     * @param appointmentID
     * @return
     */
    public static boolean isOwner(int userID, int appointmentID) {
        Connection con = DBConnector.getCon();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM appointment " +
                        "WHERE ownerID = " + userID + " AND appointmentID = " + appointmentID + ";";
                System.out.println("Peforming SQL Query [" + query + "]");
                ResultSet rs = stmt.executeQuery(query);
                if (rs.getFetchSize() != 0) {
                    return true;
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
        return false;
    }

    /**
     * TODO: Create tests for this
     * @param appointmentID
     * @return
     */
    public static ArrayList<Group> getAttendingGroups(int appointmentID) {
        Connection con = DBConnector.getCon();
        ArrayList<Integer> groupIDs = new ArrayList<Integer>();
        ArrayList<Group> groups = new ArrayList<Group>();
        if (con!=null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM groupInvited " +
                        "WHERE appointmentID = '" + appointmentID + "';";
                System.out.println(("Performing SQL Query [" + query + "]"));
                ResultSet rset = stmt.executeQuery(query);
                while (rset.next()) {
                    groupIDs.add(rset.getInt("groupID"));
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        for (int groupID : groupIDs) {
            groups.add(getGroup(groupID));
        }
        return groups;
    }

    public static ArrayList<Appointment> getInvitations(int userID) {
        Connection con = DBConnector.getCon();
        ArrayList<Appointment> invitations = new ArrayList<Appointment>();
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM appointment " +
                        "INNER JOIN userInvited " +
                        "ON (appointment.appointmentID = userInvited.appointmentID) " +
                        "WHERE userID = '" + userID + "'" +
                        "AND userInvited.attending = '0';";
                ResultSet rset = stmt.executeQuery(query);
                invitations = ResultResolver.appointmentResolver(rset);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No Connection");
        }
        return invitations;
    }
}