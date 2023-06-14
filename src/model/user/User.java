package model.user;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private LocalDate birthDay;

    public User(String firstName, String lastName, String login, String password, LocalDate birthDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.birthDay = birthDay;
    }

//    public User(){}

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
