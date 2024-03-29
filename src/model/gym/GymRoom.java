package model.gym;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GymRoom implements Serializable {
    private int number;
    private String name;
    private List<Activity> activities;// lista zajęć, które odbywają się w tej sali

    private int membersNumberMax;

    public GymRoom(String name, int number , int membersNumberMax) {
        this.name = name;
        this.number = number;
        this.membersNumberMax = membersNumberMax;
        this.activities = new ArrayList<>();
    }

    public int getRoomNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMembersNumberMax() {
        return membersNumberMax;
    }

    public void setMembersNumberMax(int membersNumberMax) {
        this.membersNumberMax = membersNumberMax;
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
    public boolean isRoomOccupied(LocalDateTime startTime, LocalDateTime endTime, List<Activity> activities) {
        for (Activity activity : activities) {
            LocalDateTime activityStartTime = activity.getStartTime();
            LocalDateTime activityEndTime = activity.getEndTime();

            if (startTime.isBefore(activityEndTime) && endTime.isAfter(activityStartTime)) {
                return true;  // sala zajeta
            }
        }

        return false;  // sala wolna
    }

    @Override
    public String toString() {
        return "Pokój nr " + number + ": " + name;
    }

}
