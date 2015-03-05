package no.ntnu.stud.model;

import no.ntnu.stud.model.Appointment;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Created by sklirg on 20/02/15.
 */
public class AppointmentTest {
    String title, description, location;
    int ownerID, roomID, maxAttending;
    @Before
    public void init() {
        title = "Mote i dag";
        description = "Et lite mote";
        location = "Skogen 3";

        ownerID = 1;
        roomID = 1;
    }

    @Test
    public void testBookingPositiveAttendees() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        int attendees = 1;
        Appointment res = new Appointment(title, date, startTime, endTime, ownerID, description, location, roomID, attendees);
        assertEquals(attendees, res.getAttending());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingNegativeAttendees() {

        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        int attendees = -1;
        Appointment res = new Appointment(title, date, startTime, endTime, ownerID, description, location, roomID, attendees);
        assertNull(res);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingEndBeforeStart() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime startTime = LocalTime.of(13, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        int attendees = 1;
        Appointment res = new Appointment(title, date, startTime, endTime, ownerID, description, location, roomID, attendees);
        assertNull(res);
    }

    @Test
    public void testBookingPassedDate() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        int attendees = 1;
        Appointment res = new Appointment(title, date, startTime, endTime, ownerID, description, location, roomID, attendees);
        assertEquals(startTime, res.getStart());
        assertEquals(endTime, res.getEnd());
    }

    @Test
    public void testBookingStartBeforeEnd() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(13, 0);
        int attendees = 1;
        Appointment res = new Appointment(title, date, startTime, endTime, ownerID, description, location, roomID, attendees);
        assertEquals(startTime, res.getStart());
        assertEquals(endTime, res.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingStartTimeEqualsEndTime() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime startTime = LocalTime.of(12, 0);
        int attendees = 1;
        Appointment res = new Appointment(title, date, startTime, startTime, ownerID, description, location, roomID, attendees);
        assertNull(res);
    }
}
