package model.user;

import interfaces.UserFactoryInterface;

import java.time.LocalDate;

public class ManagerFactory implements UserFactoryInterface {
    public User create(String firstName, String lastName, String login, String password, LocalDate birthDay) {
        return new Manager(firstName, lastName, login, password, birthDay, 5000, "Szybki", User.ROLE_CLUB_MEMBER);
    }
}