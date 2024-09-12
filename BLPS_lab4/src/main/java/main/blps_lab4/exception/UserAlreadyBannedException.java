package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class UserAlreadyBannedException extends BaseException {
    private final String login;

    public UserAlreadyBannedException(String login) {
        super("Пользователь уже забанен");
        this.login = login;
    }
}
