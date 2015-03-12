package no.ntnu.stud.util;

import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Group;
import no.ntnu.stud.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by adrianh on 09.03.15.
 */
public class ResultResolver {

    public static ArrayList<Appointment> appointmentResolver(ResultSet appointmentResult) throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        while (appointmentResult.next()) {
            int appointmentID = appointmentResult.getInt("appointmentID");
            String title = appointmentResult.getString("title");
            LocalDate date = appointmentResult.getTimestamp("appointmentDate").toLocalDateTime().toLocalDate();
            LocalTime startTime = appointmentResult.getTimestamp("startTime").toLocalDateTime().toLocalTime();
            LocalTime endTime = appointmentResult.getTimestamp("endTime").toLocalDateTime().toLocalTime();
            int ownerID = appointmentResult.getInt("ownerID");
            String description = appointmentResult.getString("description");
            String location = appointmentResult.getString("location");
            int roomID = appointmentResult.getInt("roomID");
            int attending = appointmentResult.getInt("attending");
            LocalDateTime alarmTime = LocalDateTime.parse("0001-01-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            if(appointmentResult.getTimestamp("alarmTime") != null){
                alarmTime = appointmentResult.getTimestamp("alarmTime").toLocalDateTime();
            }
            appointments.add(new Appointment(appointmentID, title, date, startTime, endTime, ownerID, description, location, roomID, attending, alarmTime));
        }
        return appointments;
    }

    public static Group groupResolver(ResultSet groupResult) throws SQLException {
        Group users = new Group();
        while (groupResult.next()) {
            int userID = groupResult.getInt("userID");
            String lastName = groupResult.getString("lastName");
            String middleName = groupResult.getString("middleName");
            String givenName = groupResult.getString("givenName");
            String email = groupResult.getString("email");
            User user = new User((userID), (lastName), (middleName), (givenName), (email));
            users.add(user);
        }
        return users;
    }

    public static User user(ResultSet userResult) throws SQLException {
        User user = null;
        while (userResult.next()) {
            int userID = userResult.getInt("userID");
            String lastName = userResult.getString("lastName");
            String middleName = userResult.getString("middleName");
            String givenName = userResult.getString("givenName");
            String email = userResult.getString("email");
            boolean superuser = userResult.getBoolean("superuser");
            return new User(userID, (lastName), (middleName), (givenName), (email), (superuser));
        }
        return null;
    }
}
