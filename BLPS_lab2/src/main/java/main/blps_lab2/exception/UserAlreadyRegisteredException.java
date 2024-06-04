package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class UserAlreadyRegisteredException extends Exception {
    private final String email;

    public UserAlreadyRegisteredException(String email) {
        super("Такой пользователь уже зарегистрирован");
        this.email = email;
    }
}

