package no.ntnu.stud.reservation;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by sklirg on 20/02/15.
 */
public class AppointmentTest {

    @Test
    public void testBookingPositiveAttendees() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 13, 0);
        Appointment res = new Appointment(start, end, 1);
        assertEquals(start, res.getStart());
        assertEquals(end, res.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingNegativeAttendees() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 13, 0);
        Appointment res = new Appointment(start, end, -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingDifferentStartEndDate() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 2, 13, 0);
        Appointment res = new Appointment(start, end, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingEndBeforeStart() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 13, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 12, 0);
        Appointment res = new Appointment(start, end, 1);
    }

    @Test
    public void testBookingStartBeforeEnd() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 13, 0);
        Appointment res = new Appointment(start, end, 1);
        assertEquals(start, res.getStart());
        assertEquals(end, res.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingStartTimeEqualsEndTime() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 12, 0);
        Appointment res = new Appointment(start, end, 1);
    }
}
