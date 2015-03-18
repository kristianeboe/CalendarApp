package no.ntnu.stud.model;

import java.sql.Timestamp;

/**
 * Created by Adrian on 12.03.2015.
 */
public class Alarm {

    private Timestamp alarmTime;
    private User user;
    private Appointment appointment;
    private int numberOfType;

    private String type;

    public Alarm(User user, Appointment appointment, String numberOfType, String type) {
        this.user = user;
        this.appointment = appointment;
        this.type = type;
        this.numberOfType = Integer.valueOf(numberOfType);
        switch (type) {
            default:
                break;
            case "minutes":
                this.alarmTime = Timestamp.valueOf(appointment.getDateTimeStart().minusMinutes(Integer.valueOf(numberOfType)));
                break;
            case "hours":
                this.alarmTime = Timestamp.valueOf(appointment.getDateTimeStart().minusHours(Integer.valueOf(numberOfType)));
                break;
            case "days":
                this.alarmTime = Timestamp.valueOf(appointment.getDateTimeStart().minusDays(Integer.valueOf(numberOfType)));
                break;
            case "weeks":
                this.alarmTime = Timestamp.valueOf(appointment.getDateTimeStart().minusWeeks(Integer.valueOf(numberOfType)));
                break;
        }
    }

    public Alarm(User user, Appointment appointment, Timestamp alarmTime, int numberOfType, String type) {
        this.user = user;
        this.appointment = appointment;
        this.alarmTime = alarmTime;
        this.numberOfType = numberOfType;
        this.type = type;
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

    public int getNumberOfType() {
        return numberOfType;
    }

    public void setNumberOfType(int numberOfType) {
        this.numberOfType = numberOfType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
