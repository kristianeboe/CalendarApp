package no.ntnu.stud.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by sklirg on 20/02/15.
 */
public class Appointment {
    private int appointmentID, ownerID, roomID;
    private String title, location, description;
    private LocalDate date;
    private LocalTime start, end;
    private LocalDateTime alarmTime;
    private int attending;

    public Appointment(LocalDate date, LocalTime start, LocalTime end, int attending) {
        setDateTime(date, start, end);
        setAttending(attending);
    }

    public Appointment(int appointmentID, String title, LocalDate date, LocalTime startTime, LocalTime endTime, int ownerID, String description, String location, int roomID, int attending, LocalDateTime alarmTime) {
        setDateTime(date, startTime, endTime);
        setAttending(attending);

        // appointmentID is something we get from database after appointment is created, and should be used to instantiate Appointments.
        this.appointmentID=appointmentID;

        // Do we need any validation for this?
        // We could implement a generic textInputValidator, which validates that the input is not empty, not longer than x chars +++
        this.title=title;

        // Get from DB. Map to User-object?
        this.ownerID=ownerID;

        // Do we need any validation for this?
        this.location =location;

        // Get from DB. Map to Room-object?
        this.roomID=roomID;

        // Do we need any validation for this?
        this.description = description;

        // Should alarmTime be at appointment start, 15 minutes before, 60 minutes before…
        this.alarmTime = alarmTime;
    }

    public void setAttending(int attending) {
        if (attending < 0)
            throw new IllegalArgumentException("Negative number of attendees is not allowed.");
        this.attending = attending;
    }

    public void setDateTime(LocalDate date, LocalTime start, LocalTime end) {
        if (end.compareTo(start) == -1)
            throw new IllegalArgumentException("End time occurs before start time");
        else if (start.equals(end))
            throw new IllegalArgumentException("Start time same as end time");
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public LocalDate getDate() { return date; }

    public String getTitle(){ return title.toString(); }

    public LocalDateTime getDateTimeStart() {
        return LocalDateTime.of(date, start);
    }

    public LocalDateTime getDateTimeEnd() {
        return LocalDateTime.of(date, end);
    }

    public int getAttending() {
        return attending;
    }
}
