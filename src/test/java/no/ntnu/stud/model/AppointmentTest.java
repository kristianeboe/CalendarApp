package no.ntnu.stud.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by sklirg on 20/02/15.
 */
public class AppointmentTest {
    String title, description, location;
    int roomID, attendees;
    int owner;
    LocalDate date;
    LocalTime startTime, endTime;
    @Before
    public void init() {
        title = "Mote i dag";
        description = "Et lite mote";
        location = "Skogen 3";

        attendees = 1;
        owner = 1;
        roomID = 1;

        date = LocalDate.of(2015, 1, 1);
        startTime = LocalTime.of(12, 0);
        endTime = LocalTime.of(13, 0);
    }

    @Test
    public void testBookingPositiveAttendees() {
        Appointment res = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, attendees);
        assertEquals(attendees, res.getAttending());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingNegativeAttendees() {
        int attendees = -1;
        Appointment res = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, attendees);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingEndBeforeStart() {
        startTime = LocalTime.of(13, 0);
        endTime = LocalTime.of(12, 0);
        Appointment res = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, attendees);
    }

    @Test
    public void testBookingPassedDate() {
        Appointment res = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, attendees);
        assertEquals(startTime, res.getStart());
        assertEquals(endTime, res.getEnd());
    }

    @Test
    public void testBookingStartBeforeEnd() {
        Appointment res = new Appointment(title, date, startTime, endTime, owner, description, location, roomID, attendees);
        assertEquals(startTime, res.getStart());
        assertEquals(endTime, res.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingStartTimeEqualsEndTime() {
        Appointment res = new Appointment(title, date, startTime, startTime, owner, description, location, roomID, attendees);
    }
}
