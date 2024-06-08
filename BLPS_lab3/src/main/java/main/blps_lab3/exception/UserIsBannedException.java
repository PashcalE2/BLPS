package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class UserIsBannedException extends Exception {
    private final Long userId;

    public UserIsBannedException(Long userId) {
        super("Этот пользователь забанен");
        this.userId = userId;
    }
}
