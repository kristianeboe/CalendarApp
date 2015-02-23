package no.ntnu.stud.reservation;

import no.ntnu.stud.reservation.Reservation;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by sklirg on 20/02/15.
 */
public class ReservationTest {

    @Test
    public void testBookingPositiveAttendees() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 13, 0);
        Reservation res = new Reservation(start, end, 1);
        assertEquals(start, res.getStart());
        assertEquals(end, res.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingNegativeAttendees() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 13, 0);
        Reservation res = new Reservation(start, end, -1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingDifferentStartEndDate() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 2, 13, 0);
        Reservation res = new Reservation(start, end, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingEndBeforeStart() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 13, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 12, 0);
        Reservation res = new Reservation(start, end, 1);
    }

    @Test
    public void testBookingStartBeforeEnd() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 13, 0);
        Reservation res = new Reservation(start, end, 1);
        assertEquals(start, res.getStart());
        assertEquals(end, res.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingStartTimeEqualsEndTime() {
        LocalDateTime start = LocalDateTime.of(2015, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 1, 1, 12, 0);
        Reservation res = new Reservation(start, end, 1);
    }
}
