package no.ntnu.stud.model;

import no.ntnu.stud.model.Appointment;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Created by sklirg on 20/02/15.
 */
public class AppointmentTest {

    @Test
    public void testBookingPositiveAttendees() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(13, 0);
        int attendees = 1;
        Appointment res = new Appointment(date, start, end, attendees);
        assertEquals(attendees, res.getAttending());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingNegativeAttendees() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(13, 0);
        Appointment res = new Appointment(date, start, end, -1);
        assertNull(res);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingEndBeforeStart() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime start = LocalTime.of(13, 0);
        LocalTime end = LocalTime.of(12, 0);
        Appointment res = new Appointment(date, start, end, 1);
        assertNull(res);
    }

    @Test
    public void testBookingPassedDate() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(13, 0);
        Appointment res = new Appointment(date, start, end, 1);
        assertEquals(start, res.getStart());
        assertEquals(end, res.getEnd());
    }

    @Test
    public void testBookingStartBeforeEnd() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(13, 0);
        Appointment res = new Appointment(date, start, end, 1);
        assertEquals(start, res.getStart());
        assertEquals(end, res.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingStartTimeEqualsEndTime() {
        LocalDate date = LocalDate.of(2015, 1, 1);
        LocalTime start = LocalTime.of(12, 0);
        Appointment res = new Appointment(date, start, start, 1);
        assertNull(res);
    }
}
