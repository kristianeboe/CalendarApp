package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.*;
import no.ntnu.stud.util.ResultResolver;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Class for getting data from the database.
 * @author Adrian Hundseth
 */
public class GetData {
    private static Logger logger = Logger.getLogger("GetData");

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
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);
                user = ResultResolver.user(rset);

                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No Connection");
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
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                user = ResultResolver.user(rset);

                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No Connection");
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
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                users = ResultResolver.groupResolver(rset);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
        }
        return users;
    }

    public static ArrayList<User> getUsersInGroup(int groupID){
        Connection con = DBConnector.getCon();
        ArrayList<User> users = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM user NATURAL JOIN userInGroup WHERE groupID = "+groupID+";";
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                users = ResultResolver.groupResolver(rset);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
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
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                users = ResultResolver.groupResolver(rset);

                String query = "SELECT * FROM userGroup WHERE groupID = " + groupID + ";";
                rset = stmt.executeQuery(query);
                logger.trace("Performing SQL Query [" + strSelect + "]");
                while (rset.next()) {
                    users.setGroupID(rset.getInt("groupID"));
                    users.setName(rset.getString("name"));
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
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
                ResultSet appointmentResult = stmt.executeQuery(strSelect);
                logger.trace("Performing SQL Query [" + strSelect + "]");
                appointmentResult.next();

                int appID = appointmentResult.getInt("appointmentID");
                String title = appointmentResult.getString("title");
                LocalDate date = appointmentResult.getTimestamp("appointmentDate").toLocalDateTime().toLocalDate();
                LocalTime startTime = appointmentResult.getTimestamp("startTime").toLocalDateTime().toLocalTime();
                LocalTime endTime = appointmentResult.getTimestamp("endTime").toLocalDateTime().toLocalTime();
                int ownerID = appointmentResult.getInt("ownerID");
                String description = appointmentResult.getString("description");
                String location = appointmentResult.getString("location");
                int roomID = appointmentResult.getInt("roomID");
                int attending = appointmentResult.getInt("attending");
                
                appointment = new Appointment(appointmentID, title, date, startTime, endTime, ownerID, description, location, roomID, attending);
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
                logger.trace("Performing SQL Query [" + strSelect + "]");
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

    public static ArrayList<Appointment> getAppointments(User user, int limit, boolean onlyAttending){
        int userID = user.getUserID();
        Connection con = DBConnector.getCon();
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql ="";
        if(onlyAttending){
            sql = "SELECT * FROM userInvited NATURAL JOIN user JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE userID = "+userID+" AND (userInvited.attending = '1') ORDER BY appointmentDate, startTime ASC LIMIT "+limit+";";
        }else{
            sql = "SELECT * FROM userInvited NATURAL JOIN user JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE userID = "+userID+" AND (userInvited.attending = '1' OR userInvited.attending = '0') ORDER BY appointmentDate, startTime ASC LIMIT "+limit+";";
        }
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                logger.trace("Performing SQL Query [" + sql + "]");
                appointments = ResultResolver.appointmentResolver(rs);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.print("No Connection");
        }
        return appointments;
    }

    public static ArrayList<Appointment> getAppointments(User user, String dateStr){
        int userID = user.getUserID();
        Connection con = DBConnector.getCon();
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql ="";
            sql = "SELECT * FROM userInvited NATURAL JOIN user JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE userID = "+userID+" AND appointmentDate ='"+dateStr+"' AND (userInvited.attending='1' OR userInvited.attending ='0') ORDER BY startTime ASC;";
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                logger.trace("Performing SQL Query [" + sql + "]");
                appointments = ResultResolver.appointmentResolver(rs);
                con.close();
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
                logger.trace("Performing SQL Query [" + strSelect + "]");
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
                logger.trace("Performing SQL Query [" + sql + "]");
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
                    logger.warn("No room is available");
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

    public Room getRoom(int roomID){
        Connection con = new DBConnector().getCon();
        Room room = null;
        if(con!=null){
            try{
                String sql = "SELECT * FROM room WHERE roomID = "+roomID+";";
                Statement stmt = con.createStatement();
                logger.trace("Performing SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                room = new Room(rs.getInt("roomID"), rs.getString("name"), rs.getInt("capacity"));
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            System.err.print("No Connection");
        }
        return room;
    }

    public static ArrayList<Inevitable> getInvited(Appointment appointment){
        Connection con = DBConnector.getCon();
        ArrayList<Inevitable> users = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM userInvited NATURAL JOIN user JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE appointment.appointmentID = "+appointment.getAppointmentID()+" ORDER BY lastName ASC;";
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rs = stmt.executeQuery(strSelect);
                while(rs.next()){
                    int userID = rs.getInt("userID");
                    String lastName = rs.getString("lastName");
                    String middleName = rs.getString("middleName");
                    String givenName = rs.getString("givenName");
                    String email = rs.getString("email");
                    users.add(new User(userID, (lastName), (middleName), (givenName), (email)));
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
        }
        return users;
    }

    public static ArrayList<Inevitable> getAccepted(Appointment appointment){
        Connection con = DBConnector.getCon();
        ArrayList<Inevitable> users = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM userInvited NATURAL JOIN user JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE appointment.appointmentID = "+appointment.getAppointmentID()+" AND userInvited.attending = '1' ORDER BY lastName ASC;";
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rs = stmt.executeQuery(strSelect);
                while(rs.next()){
                    int userID = rs.getInt("userID");
                    String lastName = rs.getString("lastName");
                    String middleName = rs.getString("middleName");
                    String givenName = rs.getString("givenName");
                    String email = rs.getString("email");
                    users.add(new User(userID, (lastName), (middleName), (givenName), (email)));
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
        }
        return users;
    }

    public static ArrayList<Inevitable> getDeclined(Appointment appointment){
        Connection con = DBConnector.getCon();
        ArrayList<Inevitable> users = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT * FROM userInvited NATURAL JOIN user JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE appointment.appointmentID = "+appointment.getAppointmentID()+" AND userInvited.attending = '2' ORDER BY lastName ASC;";
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rs = stmt.executeQuery(strSelect);
                while(rs.next()){
                    int userID = rs.getInt("userID");
                    String lastName = rs.getString("lastName");
                    String middleName = rs.getString("middleName");
                    String givenName = rs.getString("givenName");
                    String email = rs.getString("email");
                    users.add(new User(userID, (lastName), (middleName), (givenName), (email)));
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
        }
        return users;
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
                logger.trace("Performing SQL Query [" + sql + "]");
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

    public static ArrayList<Notification> getNotifications(User user){
        int userID = user.getUserID();
        Connection con = DBConnector.getCon();
        ArrayList<Notification> notifications = new ArrayList<>();
        if(con != null){
            try {
                Statement stmt = con.createStatement();
                String sql = "SELECT notificationID, message, appointmentID FROM notification NATURAL JOIN hasNotification WHERE userID="+userID+" AND seen = '0';";
                logger.trace("Performing SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    int notificationID = rs.getInt("notificationID");
                    int appointmentID = rs.getInt("appointmentID");
                    Appointment appointment = GetData.getAppointment(appointmentID);
                    String message = rs.getString("message");
                    notifications.add(new Notification(notificationID, appointment, message));
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
                logger.debug("Peforming SQL Query [" + query + "]");
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
                logger.debug("Peforming SQL Query [" + query + "]");
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
                logger.debug(("Performing SQL Query [" + query + "]"));
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
                        "AND userInvited.attending = '0' AND (appointment.appointmentDate >= CURDATE()) ORDER BY appointment.appointmentDate, appointment.startTime ASC;";
                ResultSet rset = stmt.executeQuery(query);
                invitations = ResultResolver.appointmentResolver(rset);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No Connection");
        }
        return invitations;
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

    public static ArrayList<Group> getGroups(User user, boolean isOwner) {
        Connection con = DBConnector.getCon();
        ArrayList<Group> groups = new ArrayList<>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String strSelect ="";
                if(isOwner){
                    strSelect = "SELECT * FROM user NATURAL JOIN userInGroup NATURAL JOIN userGroup WHERE userID = "+user.getUserID()+" AND userInGroup.isOwner = 1 ORDER BY userGroup.name ASC;";
                }else{
                    strSelect = "SELECT * FROM user NATURAL JOIN userInGroup NATURAL JOIN userGroup WHERE userID = "+user.getUserID()+" AND (userInGroup.isOwner = NULL OR userInGroup.isOwner = 0) ORDER BY userGroup.name ASC;";
                }
                logger.trace("Performing SQL Query [" + strSelect + "]");
                ResultSet rset = stmt.executeQuery(strSelect);

                while(rset.next()){
                    String name = rset.getString("name");
                    int id = rset.getInt("groupID");

                    groups.add(new Group(id, name));
                }

                logger.trace("Performing SQL Query [" + strSelect + "]");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
        }
        return groups;
    }

    public static ArrayList<Alarm> getAlarms(User user) {
        Connection con = DBConnector.getCon();
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM alarm WHERE userID = '" + user.getUserID() + "'";
                logger.info("Performing SQL Query [" + query + "]");
                ResultSet rset = stmt.executeQuery(query);

                while (rset.next()) {
                    int appointmentID = rset.getInt("appointmentID");
                    Timestamp alarmTime = rset.getTimestamp("alarmTime");
                    int numberOfType = rset.getInt("numberOfType");
                    String beforeUnit = rset.getString("beforeUnit");
                    Alarm alarm = new Alarm(user, getAppointment(appointmentID), alarmTime, numberOfType, beforeUnit);
                    alarms.add(alarm);
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No Connection");
        }
        return alarms;
    }

    public static ArrayList<Alarm> getAlarms(Appointment appointment) {
        Connection con = DBConnector.getCon();
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM alarm WHERE appointmentID = '" + appointment.getAppointmentID() + "'";
                logger.info("Performing SQL Query [" + query + "]");
                ResultSet rset = stmt.executeQuery(query);

                while (rset.next()) {
                    int userID = rset.getInt("userID");
                    Timestamp alarmTime = rset.getTimestamp("alarmTime");
                    int numberOfType = rset.getInt("numberOfTypes");
                    String beforeUnit = rset.getString("beforeUnit");
                    Alarm alarm = new Alarm(getUser(userID), appointment, alarmTime, numberOfType, beforeUnit);
                    alarms.add(alarm);
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No Connection");
        }
        return alarms;
    }

    public static ArrayList<Alarm> getAlarms() {
        Connection con = DBConnector.getCon();
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM alarm;";
                logger.info("Performing SQL Query [" + query + "]");
                ResultSet rset = stmt.executeQuery(query);

                while (rset.next()) {
                    int userID = rset.getInt("userID");
                    int appointmentID = rset.getInt("appointmentID");
                    Timestamp alarmTime = rset.getTimestamp("alarmTime");
                    int numberOfType = rset.getInt("numberOfType");
                    String beforeUnit = rset.getString("beforeUnit");
                    Alarm alarm = new Alarm(getUser(userID), getAppointment(appointmentID), alarmTime, numberOfType, beforeUnit);
                    alarms.add(alarm);
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No connection");
        }
        return alarms;
    }

    public static int userIsAvailable(User user, LocalTime startTime, LocalTime endTime, LocalDate date){
        Connection con = DBConnector.getCon();
        int status = 1;
        if(con != null){
            try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM user NATURAL JOIN userInvited JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE user.userID="+user.getUserID()+";";
                logger.debug("Peforming SQL Query [" + sql + "]");
                ResultSet rs = stmt.executeQuery(sql);

                while(rs.next()){
                    int appointmentID = rs.getInt("appointmentID");
                    String from_time = startTime.toString();
                    String to_time = endTime.toString();
                    String dt = date.toString();
                    Statement stmt2 = con.createStatement();
                    String sql2 = "SELECT userInvited.attending FROM user NATURAL JOIN userInvited JOIN appointment ON(userInvited.appointmentID = appointment.appointmentID) WHERE user.userID="+user.getUserID()+" "+
                            "AND appointment.appointmentID="+appointmentID+" " +
                            "AND (('"+from_time+"' > startTime "+
                            "AND '"+from_time+"' <  endTime) " +
                            "OR ('"+to_time+"'> startTime "+
                            "AND '"+to_time+"'< endTime) "+
                            "OR ('"+from_time+"' < startTime "+
                            "AND '"+to_time+"' >  endTime ))" +
                            "AND '"+dt+"' = appointmentDate;";
                    logger.debug("Peforming SQL Query [" + sql2 + "]");
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    if(rs2.next()){
                        int attending = Integer.parseInt(rs2.getString("attending"));
                        if(attending == 1){
                            return 2;
                        }else if(attending == 0){
                            status = 0;
                        }
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            System.err.print("No connection");
        }
        return status;
    }

    public static Room getRoomById(int roomId) {
        Connection con = DBConnector.getCon();
        Room room = null;

        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                String query = "SELECT * FROM room WHERE roomID = '" + roomId + "'";
                logger.info("[GetRoomById] Performing SQL Query [" + query + "]");
                ResultSet rset = stmt.executeQuery(query);

                while (rset.next()) {
                    String name = rset.getString("name");
                    int capacity = rset.getInt("capacity");
                    room = new Room(roomId, name, capacity);
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No Connection");
        }
        return room;
    }
}