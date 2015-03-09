package no.ntnu.stud.util;

import no.ntnu.stud.jdbc.GetData;
import no.ntnu.stud.model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            LocalDateTime alarmTime = appointmentResult.getTimestamp("alarmTime").toLocalDateTime();
            appointments.add(new Appointment(appointmentID, title, date, startTime, endTime, GetData.getUser(ownerID), description, location, roomID, attending, alarmTime));
        }
        return appointments;
    }
}
