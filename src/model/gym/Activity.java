package model.gym;

import model.user.ClubMember;
import model.user.Worker;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Activity implements Serializable {
    private static int nextId = 1;
    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Worker worker;
    private List<ClubMember> clubMembers;

    public Activity(String name, LocalDateTime startTime, LocalDateTime endTime, Worker worker) {
        this.id = nextId++;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.worker = worker;
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

    public LocalDateTime getDateTime() {
        return startTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.startTime = dateTime;
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

    public List<ClubMember> getMembers() {
        return clubMembers;
    }

    public void addToActivity(ClubMember clubMember) {
        this.clubMembers.add(clubMember);
    }

    public void removeFromActivity(ClubMember clubMember) {
        this.clubMembers.remove(clubMember);
    }

    public boolean canJoin(){
        return LocalDateTime.now().isBefore(startTime);
    }

    @Override
    public String toString() {
        return name + " " + "Godzina rozpoczęcia: " + startTime + " Prowadzący " + worker;
    }
}
