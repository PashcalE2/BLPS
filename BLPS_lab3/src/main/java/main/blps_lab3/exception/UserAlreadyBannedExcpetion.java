package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class UserAlreadyBannedExcpetion extends Exception {
    private final String login;

    public UserAlreadyBannedExcpetion(String login) {
        super("Пользователь уже забанен");
        this.login = login;
    }
}
