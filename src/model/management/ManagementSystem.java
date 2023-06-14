package model.management;

import model.gym.Activity;
import model.gym.GymRoom;
import model.user.ClubMember;
import model.user.Manager;
import model.user.Worker;

import java.util.ArrayList;
import java.util.List;

public class ManagementSystem {
    private List<ClubMember> clubMembers;
    private List<Worker> workers;
    private List<Manager> managers;
    private List<Activity> activities;
    private List<GymRoom> gymRooms;

    public ManagementSystem() {
        this.clubMembers = new ArrayList<>();
        this.workers = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.gymRooms = new ArrayList<>();
    }

    public void addClubMember(ClubMember clubMember) {
        this.clubMembers.add(clubMember);
    }

    public void addWorker(Worker worker) {
        this.workers.add(worker);
    }

    public void addManager(Manager manager) {
        this.managers.add(manager);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void addGymRoom(GymRoom gymRoom) {
        this.gymRooms.add(gymRoom);
    }
}
