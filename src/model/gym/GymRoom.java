package model.gym;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GymRoom implements Serializable {
    private int number;
    private String name;
    private List<Activity> activities;  // lista zajęć, które odbywają się w tej sali

    public GymRoom(String name, int number) {
        this.name = name;
        this.number = number;
        this.activities = new ArrayList<>();
    }

    public int getRoomNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Pokój nr " + number + ": " + name;
    }
}
