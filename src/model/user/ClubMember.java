package model.user;

import model.gym.Activity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClubMember extends User implements Serializable {
    private static int nextId = 1;
    private int id;
    private String accountStatus;
    private boolean isPaid = false;
    private List<Activity> activities;  // lista zajęć, na które jest zapisany klubowicz

    public ClubMember(String firstName, String lastName, String login, String password, LocalDate birthDay, String userRole) {
        super(firstName, lastName, login, password, birthDay,userRole);
        this.id = nextId++;
        this.activities = new ArrayList<>();
    }

//    public ClubMember(){}

    public int getId() {
        return id;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

//    public boolean isPaid() {
//        return isPaid;
//    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void signUpForActivity(Activity activity) {
        this.activities.add(activity);
    }


    public void unsubscribeFromActivity(Activity activity) {
        this.activities.remove(activity);
    }

}
