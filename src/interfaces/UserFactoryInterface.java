package interfaces;

import model.user.User;

import java.time.LocalDate;

public interface UserFactoryInterface {
    User create(String firstName, String lastName, String login, String password, LocalDate birthDay);
}