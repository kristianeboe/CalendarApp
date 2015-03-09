package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.*;

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

                while (rset.next()) {
                    String lastName = rset.getString("lastName");
                    String middleName = rset.getString("middleName");
                    String givenName = rset.getString("givenName");
                    String email = rset.getString("email");
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
                    String lastName = rset.getString("lastName");
                    String middleName = rset.getString("middleName");
                    String givenName = rset.getString("givenName");
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
        ArrayList<User> users = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    int userID = rset.getInt("userID");
                    String lastName = rset.getString("lastName");
                    String middleName = rset.getString("middleName");
                    String givenName = rset.getString("givenName");
                    String email = rset.getString("email");
                    User user = new User(userID, (lastName), (middleName), (givenName), (email));
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

    public static ArrayList<User> getUsersInGroup(int groupID) {
        Connection con = DBConnector.getCon();
        ArrayList<User> users = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user NATURAL JOIN userInGroup WHERE groupID = "+groupID+";";
                System.out.println("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    int userID = rset.getInt("userID");
                    String lastName = rset.getString("lastName");
                    String middleName = rset.getString("middleName");
                    String givenName = rset.getString("givenName");
                    String email = rset.getString("email");
                    User user = new User((userID), (lastName), (middleName), (givenName), (email));
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

                while (rset.next()) {
                    int ID = rset.getInt("appointmentID");
                    String title = rset.getString("title");
                    User owner = User.getById(rset.getInt("ownerID"));
                    LocalDate date = rset.getTimestamp("appointmentDate").toLocalDateTime().toLocalDate();
                    LocalTime from = rset.getTimestamp("startTime").toLocalDateTime().toLocalTime();
                    LocalTime to = rset.getTimestamp("endTime").toLocalDateTime().toLocalTime();
                    String location = rset.getString("location");
                    int roomID = rset.getInt("roomID");
                    String description = rset.getString("description");
                    int attending = rset.getInt("attending");
                    LocalDateTime alarmTime = LocalDateTime.parse("0001-01-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    if(rset.getTimestamp("alarmTime") != null){
                        alarmTime = rset.getTimestamp("alarmTime").toLocalDateTime();
                    }
                    //System.out.println("id: "+ID+", ownerID: "+ownerID+", date: "+date.toString()+", from: " +from.toString()+", to: "+to.toString()+", location: "+location+", roomID: "+roomID+", description: "+description+", attening: "+attending+", alarmTime: "+alarmTime.toString());

                    appointment = new Appointment(ID, title, date, from, to, owner, description, location, roomID, attending, alarmTime);
                }
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

                while (rset.next()) {
                    int ID = rset.getInt("appointmentID");
                    String title = rset.getString("title");
                    User owner = getUser(rset.getInt("ownerID"));
                    LocalDate date = rset.getTimestamp("appointmentDate").toLocalDateTime().toLocalDate();
                    LocalTime from = rset.getTimestamp("startTime").toLocalDateTime().toLocalTime();
                    LocalTime to = rset.getTimestamp("endTime").toLocalDateTime().toLocalTime();
                    String location = rset.getString("location");
                    int roomID = rset.getInt("roomID");
                    String description = rset.getString("description");
                    int attending = rset.getInt("attending");
                    LocalDateTime alarmTime = LocalDateTime.parse("0001-01-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    if(rset.getTimestamp("alarmTime") != null){
                        alarmTime = rset.getTimestamp("alarmTime").toLocalDateTime();
                    }
                    //System.out.println("id: "+ID+", ownerID: "+ownerID+", date: "+date.toString()+", from: " +from.toString()+", to: "+to.toString()+", location: "+location+", roomID: "+roomID+", description: "+description+", attening: "+attending+", alarmTime: "+alarmTime.toString());

                    appointment = new Appointment(ID, title, date, from, to, owner, description, location, roomID, attending, alarmTime);
                }
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
        String sql = "SELECT * FROM userAttends NATURAL JOIN user JOIN appointment ON(userAttends.appointmentID = appointment.appointmentID) WHERE userID = "+userID+" ORDER BY appointmentDate, startTime ASC LIMIT "+limit+";";
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
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.err.print("No Connection");
        }
        return notifications;
    }

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
                while(rs.next()) {
                    int appointmentID = rs.getInt("appointmentID");
                    String title = rs.getString("title");
                    LocalDate date = rs.getTimestamp("appointmentDate").toLocalDateTime().toLocalDate();
                    LocalTime startTime = rs.getTimestamp("startTime").toLocalDateTime().toLocalTime();
                    LocalTime endTime = rs.getTimestamp("endTime").toLocalDateTime().toLocalTime();
                    int ownerID = rs.getInt("ownerID");
                    String description = rs.getString("description");
                    String location = rs.getString("location");
                    int roomID = rs.getInt("roomID");
                    int attending = rs.getInt("attending");
                    LocalDateTime alarmTime = rs.getTimestamp("alarmTime").toLocalDateTime();
                    appointments.add(new Appointment(appointmentID, title, date, startTime, endTime, getUser(userID), description, location, roomID, attending, alarmTime));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
        return appointments;
    }

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
        return false;
    }

    public static ArrayList<User> searchUser(String partOfName){
        Connection con = DBConnector.getCon();
        ArrayList<User> users = new ArrayList<>();
        if (con != null) {
            try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM user WHERE CONCAT(givenName,' ',middleName,' ',lastName) LIKE '"+partOfName+"%';";
                System.out.println("Peforming SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    String lastName = rs.getString("lastName");
                    String middleName = rs.getString("middleName");
                    String givenName = rs.getString("givenName");
                    String email = rs.getString("email");
                    int userID = rs.getInt("userID");
                    users.add(new User(userID, lastName, middleName, givenName, email));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
        return users;
    }

    public static ArrayList<Group> searchGroup(String partOfName){
        Connection con = DBConnector.getCon();
        ArrayList<Group> groups = new ArrayList<>();
        if(con != null){
            try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM userGroup WHERE name LIKE '"+partOfName+"%';";
                System.out.println("Peforming SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    String name = rs.getString("name");
                    int groupID = rs.getInt("groupID");
                    groups.add(new Group(groupID, name));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
        return groups;
    }
}