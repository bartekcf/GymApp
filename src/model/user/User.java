package model.user;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private LocalDate birthDay;

    private String userRole;

    public static final String ROLE_CLUB_MEMBER  = "ClubMember";
    public static final String ROLE_WORKER  = "Worker";
    public static final String ROLE_MANAGER  = "Manager";

    public User(String firstName, String lastName, String login, String password, LocalDate birthDay, String userRole) {
        this.id = nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.birthDay = birthDay;
        this.userRole = userRole;
    }


//    public User(){}

    public int getId(){
        return id;
    }

    public static void setNextId(int nextId) {
        User.nextId = nextId;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
