package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class UserAlreadyUnbannedException extends BaseException {
    private final String login;

    public UserAlreadyUnbannedException(String login) {
        super("Пользователь уже разбанен");
        this.login = login;
    }
}
