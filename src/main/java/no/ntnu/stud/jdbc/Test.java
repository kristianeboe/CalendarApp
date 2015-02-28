package no.ntnu.stud.jdbc;

import no.ntnu.stud.model.Appointment;
import no.ntnu.stud.model.Notification;
import no.ntnu.stud.model.Room;
import no.ntnu.stud.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by kristoffer on 27.02.15.
 */
public class Test {
    private static GetData gd = new GetData();


    public static void main(String[] args) {
        LocalTime startTime, endTime;
        LocalDate date;

        //Get all notifications for user 1
        ArrayList<Notification> list;
        list = gd.getNotifications(1);
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getMessage());
        }

        //Check if room is available
        boolean available;
        startTime = LocalTime.parse("13:00");
        endTime = LocalTime.parse("16:00");
        date = LocalDate.parse("2015-02-27");
        available = gd.roomIsAvailable(2, startTime, endTime, date);
        if(available){
            System.out.println("Room is available!");
        }else{
            System.out.println("Room is booked!");
        }

        //Get smallest room with size >= 15
        Room room;
        startTime = LocalTime.parse("15:00");
        endTime = LocalTime.parse("16:00");
        date = LocalDate.parse("2015-05-01");
        room = gd.getSmallestRoom(startTime, endTime, date, 15);
        System.out.println("Room name: "+room.getName()+", capacity: "+room.getCapacity()+", roomID: "+room.getRoomID());

        //Get appointment with id=1
        //Appointment app = gd.getAppointment(1);
        //System.out.println("appointment title: " + app.getTitle());

        //Appointment appointment = new Appointment(1, "Stand-up møte",1, date, startTime, endTime, "R1", 2, "description", 5, LocalDateTime.parse("0001-01-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        //Get all users
        ArrayList<User> users = gd.getUsers();
        for(int i = 0; i < users.size(); i++){
            System.out.println(users.get(i).getEmail());
        }

        //Get user with email: kristoffer@andresen.no
        User usr = gd.getUser("kristoffer@andresen.no");
        System.out.println(usr.getGivenName()+" "+usr.getLastName());

        //Get user with id=1
        usr = gd.getUser(1);
        System.out.println(usr.getEmail()+" "+usr.getGivenName());

        //Get users in group 2
        ArrayList<User> usersInGroup = gd.getUsersInGroup(2);
        System.out.println("Users in group 2:");
        for(User user : usersInGroup){
            System.out.println(user.getGivenName()+" "+user.getLastName());
        }
    }


}
