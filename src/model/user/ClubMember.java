package model.user;

import model.gym.Activity;
import model.management.DataBase;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClubMember extends User implements Serializable {
    private String accountStatus;
    private boolean isPaid = false;
    private List<Activity> activities;  // lista zajęć, na które jest zapisany klubowicz

    public ClubMember(String firstName, String lastName, String login, String password, LocalDate birthDay, String userRole) {
        super(firstName, lastName, login, password, birthDay,userRole);
        this.activities = new ArrayList<>();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void signUpForActivity(Activity activity) {
        this.activities.add(activity);
    }


    public void signOutFromActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
            activity.removeFromActivity(this);
        }
    }

    public String getClubMemberLogin()
    {
        return this.getLogin();
    }
}
