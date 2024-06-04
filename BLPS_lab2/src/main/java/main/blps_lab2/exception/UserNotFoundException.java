package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {
    private final String login;

    public UserNotFoundException(String login) {
        super("Пользователь не найден");
        this.login = login;
    }
}
