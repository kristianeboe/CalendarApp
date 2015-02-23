package no.ntnu.stud.reservation;

import no.ntnu.stud.reservation.Reservation;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by sklirg on 20/02/15.
 */
public class ReservationTest {

    @Test
    public void testBookingPositiveAttendees() {
        Reservation res = new Reservation();
        assertEquals(true, res.validateAttending(0));
        assertEquals(true, res.validateAttending(1));
        assertEquals(true, res.validateAttending(10));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBookingNegativeAttendees() {
        Reservation res = new Reservation();
        assertEquals(true, res.validateAttending(-1));
    }
}
