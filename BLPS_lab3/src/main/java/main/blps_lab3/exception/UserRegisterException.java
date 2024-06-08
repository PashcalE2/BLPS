package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class UserRegisterException extends Exception {
    private final String email;

    public UserRegisterException(String email) {
        super("Некорректный формат почты");
        this.email = email;
    }
}
