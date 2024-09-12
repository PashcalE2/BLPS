package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class UserRegisterException extends BaseException {
    private final String email;

    public UserRegisterException(String email) {
        super("Некорректный формат почты");
        this.email = email;
    }
}
