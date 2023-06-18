package model.user;

import interfaces.UserFactoryInterface;

import java.time.LocalDate;

public class WorkerFactory implements UserFactoryInterface {
    public User create(String firstName, String lastName, String login, String password, LocalDate birthDay) {
        return new Worker(firstName, lastName, login, password, birthDay, 3500, User.ROLE_CLUB_MEMBER);
    }
}