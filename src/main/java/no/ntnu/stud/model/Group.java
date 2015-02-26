package no.ntnu.stud.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 * Created by adrianh on 26.02.15.
 */
public class Group extends ArrayList<User> {

    private IntegerProperty groupID;
    private StringProperty name;
    private ArrayList<User> group;

    public Group() {
        super();
    }

    public Group(IntegerProperty groupID, StringProperty name) {
        this.groupID = groupID;
        this.name = name;
    }

    public Group(int groupID, String name) {
        this.groupID.set(groupID);
        this.name.set(name);
    }

    public int getGroupID() {
        return groupID.get();
    }

    public IntegerProperty groupIDProperty() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID.set(groupID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
