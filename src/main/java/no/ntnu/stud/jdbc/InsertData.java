package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.Alarm;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Group;
import no.ntnu.stud.model.User;
import no.ntnu.stud.security.SHAHashGenerator;
import no.ntnu.stud.util.TimeConverter;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adrianh on 21.02.15.
 */
public class InsertData {
    private static Logger logger = Logger.getLogger("InsertData");

    public static HashMap<String, byte[]> createPasswordHashMap(String password) {
        HashMap<String, byte[]> r = new HashMap<>();
        byte[] salt = SHAHashGenerator.getSalt();
        r.put("salt", salt);
        r.put("hash", SHAHashGenerator.hash(password.toCharArray(), salt));
        return r;
    }

    public static HashMap<String, byte[]> createPasswordHashMap(byte[] hash, byte[] salt) {
        HashMap<String, byte[]> r = new HashMap<>();
        r.put("salt", salt);
        r.put("hash", hash);
        return r;
    }

    public static User createUser(User user) {
        String lastName = user.getLastName();
        String middleName = user.getMiddleName();
        String givenName = user.getGivenName();
        String email = user.getEmail();
        HashMap<String, byte[]> passwordHashMap = user.getPasswordHashMap();

        Connection con = DBConnector.getCon();
        int userID = 0;

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
                preparedStmt.setBytes(5, passwordHashMap.get("hash"));
                preparedStmt.setBytes(6, passwordHashMap.get("salt"));
                preparedStmt.execute();
                logger.debug("Performing SQL Query [" + query + "]");
            } catch (SQLException e) {
                logger.fatal("SQLException: " + e.getMessage());
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

    public static void inviteUser(User user, Appointment appointment){
        Connection con = DBConnector.getCon();

        if (con != null) {
            String query = "INSERT INTO userInvited (userID, appointmentID) VALUES(" + user.getUserID() + ", "+appointment.getAppointmentID()+");";
            try {
                logger.debug("Performing SQL Query [" + query + "]");
                Statement stmt = con.prepareStatement(query);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
        }
    }

    public static Appointment createAppointment(Appointment appointment) {
        Connection con = DBConnector.getCon();
        Appointment created_appointment = null;

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
                PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, appointment.getTitle());
                stmt.setString(2, appointment.getDate().toString());
                stmt.setString(3, appointment.getStart().toString());
                stmt.setString(4, appointment.getEnd().toString());
                stmt.setString(5, appointment.getLocation());
                stmt.setInt(6, appointment.getRoomID());
                stmt.setInt(7, appointment.getOwner());
                stmt.setInt(8, appointment.getAttending());
                if (appointment.getAlarmTime() != null) {
                    stmt.setTimestamp(9, TimeConverter.localDateTimeToTimestamp(appointment.getAlarmTime()));
                } else {
                    stmt.setTimestamp(9, null);
                }
                stmt.setString(10, appointment.getDescription());
                logger.debug("[Create Appointment] Performing SQL Query [" + query + "]");
                stmt.execute();
                String getID = "SELECT LAST_INSERT_ID()";
                logger.debug("[Create Appointment] Getting ID of created appointment");
                logger.debug("[Create Appointment] Performing SQL Query [" + getID+ "]");
                ResultSet rs = stmt.executeQuery(getID);
                rs.next();
                created_appointment = appointment;
                created_appointment.setAppointmentID(rs.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return created_appointment;
    }

    public static int createAppointmentGetID(Appointment appointment) {
        Connection con = DBConnector.getCon();
        int appointmentID = -1;
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
                PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, appointment.getTitle());
                stmt.setString(2, appointment.getDate().toString());
                stmt.setString(3, appointment.getStart().toString());
                stmt.setString(4, appointment.getEnd().toString());
                stmt.setString(5, appointment.getLocation());
                stmt.setInt(6, appointment.getRoomID());
                stmt.setInt(7, appointment.getOwner());
                stmt.setInt(8, appointment.getAttending());
                if (appointment.getAlarmTime() != null) {
                    stmt.setTimestamp(9, TimeConverter.localDateTimeToTimestamp(appointment.getAlarmTime()));
                } else {
                    stmt.setTimestamp(9, null);
                }
                stmt.setString(10, appointment.getDescription());
                stmt.execute();
                logger.debug("Performing SQL Query [" + query + "]");
                String getID = "SELECT LAST_INSERT_ID()";
                ResultSet rs = stmt.executeQuery(getID);
                rs.next();
                appointmentID = rs.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return appointmentID;
    }

    public static void bookRoom(int roomID, int appointmentID) {
        Connection con = DBConnector.getCon();

        if (con != null) {
            String query = "UPDATE appointment SET roomID = '" + roomID + "' WHERE appointmentID = '" + appointmentID + "';";
            try {
                logger.debug("Performing SQL Query [" + query + "]");
                Statement stmt = con.prepareStatement(query);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.fatal("No connection");
        }
    }

    public void setNotification(ArrayList<User> users, String message){
        Connection con = DBConnector.getCon();
        int notificationID;
        if(con != null) try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO notification(message) VALUES('" + message + "')";
            logger.debug("Performing SQL Query [" + sql + "]");
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            String getID = "SELECT LAST_INSERT_ID()";
            ResultSet rs = stmt.executeQuery(getID);
            rs.next();
            notificationID = rs.getInt(1);

            for (User user : users) {
                sql = "INSERT INTO hasNotification(userID, notificationID) VALUES(" + user.getUserID() + ", " + notificationID + ")";
                logger.debug("Performing SQL Query [" + sql + "]");
                stmt.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        else {
            logger.fatal("No Connection");
        }
    }


    public static void addToGroup(User user, int groupID){
        Connection con = DBConnector.getCon();
        if (con != null) {
            String query = "INSERT INTO userInGroup (userID, groupID) VALUES(" + user.getUserID() + ", "+groupID+");";
            try {
                logger.debug("Performing SQL Query [" + query + "]");
                Statement stmt = con.prepareStatement(query);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No Connection");
        }
    }

    public static void addSubGroup(int groupID, int subGroupID) {
        Connection con = DBConnector.getCon();
        if (con != null) {
            String query = "INSERT INTO subGroup (groupID, subGroupID) VALUES(" + groupID + ", "+ subGroupID +");";
            try {
                logger.debug("Performing SQL Query [" + query + "]");
                Statement stmt = con.prepareStatement(query);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No Connection");
        }
    }

    public void setAlarm(User user, Appointment appointment, Timestamp alarmTime) {
        Connection con = DBConnector.getCon();
        if (con != null) {
            String query = "INSERT INTO alarm (" +
                    "userID," +
                    "appointmentID," +
                    "alarmTime) VALUES(" +
                    "?, ?, ?)";
            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, user.getUserID());
                stmt.setInt(2, appointment.getAppointmentID());
                stmt.setTimestamp(3, alarmTime);
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No Connection");
        }
    }

    public void setAlarm(Alarm alarm) {
        Connection con = DBConnector.getCon();
        if (con != null) {
            String query = "INSERT INTO alarm (" +
                    "userID," +
                    "appointmentID," +
                    "alarmTime) VALUES(" +
                    "?, ?, ?)";
            try {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, alarm.getUser().getUserID());
                stmt.setInt(2, alarm.getAppointment().getAppointmentID());
                stmt.setTimestamp(3, alarm.getAlarmTime());
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("No Connection");
        }
    }

    public void createGroup(String name, Group group) {
        Connection con = DBConnector.getCon();
        int groupID;
        if (con != null) {
            try {
                String query = "INSERT INTO group (name) VALUES ('" + name + "')";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
                String getID = "SELECT LAST_INSERT_ID()";
                ResultSet rs = stmt.executeQuery(getID);
                rs.next();
                groupID = rs.getInt(1);

                if (group != null) {
                    for (Object user : group) {
                        if (user.getClass().equals(User.class)) {
                            addToGroup((User) user, groupID);
                        } else if (user.getClass().equals(Group.class)) {
                            Group subGroup = (Group) user;
                            addSubGroup(groupID, subGroup.getGroupID());
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //createUser("Normann", "", "Ola", "ola.normann@mail.no", "passord");
    }
}
