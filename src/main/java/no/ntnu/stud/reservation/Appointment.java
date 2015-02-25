package no.ntnu.stud.reservation;

import java.time.LocalDateTime;

/**
 * Created by sklirg on 20/02/15.
 */
public class Appointment {
    private LocalDateTime start, end;
    private int attending;

    public Appointment(LocalDateTime start, LocalDateTime end, int attending) {
        setDateTime(start, end);
        setAttending(attending);
    }

    public void setAttending(int attending) {
        if (attending < 0)
            throw new IllegalArgumentException("Negative number of attendees is not allowed.");
        this.attending = attending;
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
        return attending;
    }
}
