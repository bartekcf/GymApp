package model.gym;

import model.user.ClubMember;
import model.user.Worker;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity implements Serializable {
    private static int nextId = 1;
    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Worker worker;
    private GymRoom room;
    private List<ClubMember> clubMembers;

    public Activity(String name, LocalDateTime startTime, LocalDateTime endTime, Worker worker, GymRoom room) {
        this.id = nextId++;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.worker = worker;
        this.room = room;
        this.clubMembers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public GymRoom getRoom() {
        return room;
    }

    public void setRoom(GymRoom room) {
        this.room = room;
    }

    public List<ClubMember> getClubMembers() {
        return clubMembers;
    }

    public void addToActivity(ClubMember clubMember) {
        this.clubMembers.add(clubMember);
    }

    public void removeFromActivity(ClubMember clubMember) {
        this.clubMembers.remove(clubMember);
    }

    public boolean canJoin() {
        return LocalDateTime.now().isBefore(startTime);
    }

    public void removeAllMembers() {
        this.clubMembers.clear();
    }

    public void addWorker(Worker worker) {
        this.worker = worker;
    }

    public void removeWorker(Worker worker) {
        if (this.worker != null && this.worker.equals(worker)) {
            this.worker = null;
        }
    }

    @Override
    public String toString() {
        return name + " Godzina rozpoczęcia: " + startTime + " Prowadzący: " + worker + " Pokój: " + room;
    }
}
