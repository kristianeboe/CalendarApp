package no.ntnu.stud.view;

import no.ntnu.stud.SetUp;
import no.ntnu.stud.jdbc.DBConnector;
import no.ntnu.stud.jdbc.EditData;
import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by sklirg on 02/03/15.
 */
public class NewAppointmentControllerTest {
    private static Appointment appointment;
    private static User user;


    @BeforeClass
    public static void setUp() {
        Connection conn;
        conn = DBConnector.getTestCon();
        SetUp.setUpDatabase(conn);
    }

    @Before
    public void init() {
        user = new User("Test", "", "Testus", "test@test.no", "123");
        user.create();
        appointment = new Appointment("Appointment", LocalDate.of(2015, 1, 1), LocalTime.of(12, 0), LocalTime.of(13, 0), user, "Description", "Hjemme", -1, 5);
        appointment = appointment.create();
    }

    @Ignore
    public void testViewAppointmentAsInvited() {
        User invited = new User("User", "", "Invited", "invited@user.com", "456");
        invited.create();

        appointment.inviteUser(invited);
    }

    @Ignore
    public void testEditAppointmentAsOwner() {
        int id_before = appointment.getAppointmentID();
        NewAppointmentController nac = new NewAppointmentController();
        Appointment edited_appointment = nac.insertOrUpdateAppointment(appointment, user);
        assertEquals(id_before, edited_appointment.getAppointmentID());
    }
}