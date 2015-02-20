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

    @Test
    public void testFindBigEnoughRooms() {
        Reservation res = new Reservation();

        ArrayList<Room> rooms = new ArrayList<Room>(2);
        rooms.add(new Room("", "", 1));
        rooms.add(new Room("", "", 3));
        rooms.add(new Room("", "", 5));

        res.setRooms(rooms);

        assertEquals(2, res.findBigEnoughRooms(3).size());
    }

    //@Test // Remove this comment when method is made, to test its functionality.
    public void testNoBigEnoughRooms() {
        Reservation res = new Reservation();

        ArrayList<Room> rooms = new ArrayList<Room>(2);
        rooms.add(new Room("", "", 1));

        res.setRooms(rooms);

        assertEquals(0, res.findBigEnoughRooms(3));
    }
}
