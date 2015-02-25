package no.ntnu.stud.model;

/**
 * Created by Kristoffer on 25/02/2015.
 */
public class Notification {
    private String message;
    int notificationID;

    public Notification(int notificationID, String message){
        this.notificationID = notificationID;
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public int getNotificationID(){
        return notificationID;
    }
}
