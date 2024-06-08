package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {
    private final String login;

    public UserNotFoundException(String login) {
        super("Пользователь не найден");
        this.login = login;
    }
}
