package no.ntnu.stud.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by sklirg on 25/02/15.
 */
public class RoomTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeID() {
        int id = -1;
        Room room = new Room(id, "testRom", 1);
        assertEquals(id, room.getRoomID());
    }

    @Test
    public void testZeroID() {
        int id = 0;
        Room room = new Room(id, "testRom", 1);
        assertEquals(id, room.getRoomID());
    }

    @Test
    public void testPositiveID() {
        int id = 1;
        Room room = new Room(id, "testRom", 1);
        assertEquals(id, room.getRoomID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoName() {
        String name = "";
        Room room = new Room(1, name, 1);
        assertEquals(name, room.getRoomID());
    }

    public void testValidName() {
        String name = "testRom";
        Room room = new Room(1, name, 1);
        assertEquals(name, room.getRoomID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeCapacity() {
        int capacity = -1;
        Room room = new Room(1, "testRom", capacity);
        assertEquals(capacity, room.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNoCapacity() {
        int capacity = 0;
        Room room = new Room(1, "testRom", capacity);
        assertEquals(capacity, room.getCapacity());
    }

    @Test
    public void testSetPositiveCapacity() {
        int capacity = 1;
        Room room = new Room(1, "testRom", capacity);
        assertEquals(capacity, room.getCapacity());
    }

}
