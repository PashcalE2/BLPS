package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class UserIsBannedException extends BaseException {
    private final Long userId;

    public UserIsBannedException(Long userId) {
        super("Этот пользователь забанен");
        this.userId = userId;
    }
}
