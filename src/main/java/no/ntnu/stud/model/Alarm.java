package no.ntnu.stud.model;

import java.sql.Timestamp;

/**
 * Created by Adrian on 12.03.2015.
 */
public class Alarm {

    private Timestamp alarmTime;
    private User user;
    private Appointment appointment;

    public Alarm(User user, Appointment appointment, Timestamp alarmTime) {
        this.alarmTime = alarmTime;
        this.user = user;
        this.appointment = appointment;
    }

    public Timestamp getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Timestamp alarmTime) {
        this.alarmTime = alarmTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
