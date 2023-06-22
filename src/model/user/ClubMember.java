package model.user;

import model.gym.Activity;
import model.management.DataBase;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class ClubMember extends User implements Serializable {
    private String accountStatus;
    private LocalDate passExpirationDate;  // data ważności karnetu
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

    public LocalDate getPassExpirationDate() {
        return passExpirationDate;
    }

    public void setPassExpirationDate(LocalDate passExpirationDate) {
        this.passExpirationDate = passExpirationDate;
    }

    public boolean isPaid() {
        return passExpirationDate != null && passExpirationDate.isAfter(LocalDate.now());
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

    public void payMembershipFee() {
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plus(Period.ofMonths(1));  // aktualna data + 1 miesiąc
        setPassExpirationDate(expirationDate);
        DataBase db = DataBase.deserialize();
        db.updateUserStatus(this);
    }

    public String getClubMemberLogin() {
        return this.getLogin();
    }
}
