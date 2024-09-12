package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends BaseException {
    private final String login;

    public UserNotFoundException(String login) {
        super("Пользователь не найден");
        this.login = login;
    }
}
