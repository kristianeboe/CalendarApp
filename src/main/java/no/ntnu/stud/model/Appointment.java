package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

/**
 * Created by sklirg on 20/02/15.
 */
public class Appointment {
    private IntegerProperty appointmentID;
    private StringProperty title;
    private IntegerProperty ownerID;
    private LocalDateTime date;
    private LocalDateTime start, end;
    private StringProperty location;
    private IntegerProperty roomID;
    private StringProperty description;
    private LocalDateTime alarmTime;
    private IntegerProperty attending;

    public Appointment(LocalDateTime start, LocalDateTime end, int attending) {
        setDateTime(start, end);
        setAttending(attending);
    }

    public Appointment(int appointmentID, String title, int ownerID, LocalDateTime date, LocalDateTime from, LocalDateTime to, String location, int roomID, String description, int attending, LocalDateTime alarmTime) {
        this.appointmentID.set(appointmentID);
        this.title.set(title);
        this.ownerID.set(ownerID);
        this.date = date;
        this.start = from;
        this.end = to;
        this.location.set(location);
        this.roomID.set(roomID);
        this.description.set(description);
        this.attending.set(attending);
        this.alarmTime = alarmTime;
    }

    public void setAttending(int attending) {
        if (attending < 0)
            throw new IllegalArgumentException("Negative number of attendees is not allowed.");
        this.attending.set(attending);
    }

    public void setDateTime(LocalDateTime start, LocalDateTime end) {
        if (!start.toLocalDate().equals(end.toLocalDate()))
            throw new IllegalArgumentException("Start and end not same date");
        else if (end.toLocalTime().compareTo(start.toLocalTime()) == -1)
            throw new IllegalArgumentException("End time occurs before start time");
        else if (start.toLocalTime().equals(end.toLocalTime()))
            throw new IllegalArgumentException("Start time same as end time");
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getAttending() {
        return attending.getValue();
    }
}
