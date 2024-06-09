package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class UserAlreadyUnbannedExcpetion extends Exception {
    private final String login;

    public UserAlreadyUnbannedExcpetion(String login) {
        super("Пользователь уже разбанен");
        this.login = login;
    }
}
