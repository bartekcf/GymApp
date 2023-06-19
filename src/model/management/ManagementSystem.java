package model.management;

import model.gym.Activity;
import model.gym.GymRoom;
import model.user.ClubMember;
import model.user.Manager;
import model.user.Worker;

import java.util.ArrayList;
import java.util.List;

public class ManagementSystem {

    private DataBase db;
    private List<Activity> activities;
    private List<GymRoom> gymRooms;

    public ManagementSystem() {
        this.db = new DataBase();
        this.activities = new ArrayList<>();
        this.gymRooms = new ArrayList<>();
    }

    public DataBase getDb() {
        return db;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<GymRoom> getGymRooms() {
        return gymRooms;
    }

    public void addClubMember(ClubMember clubMember) {
        db.addUser(clubMember);
    }

    public void addWorker(Worker worker) {
        db.addUser(worker);
    }

    public void addManager(Manager manager) {
        db.addUser(manager);
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void addGymRoom(GymRoom gymRoom) {
        gymRooms.add(gymRoom);
    }
}
