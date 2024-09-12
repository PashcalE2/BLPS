package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class UserAlreadyRegisteredException extends BaseException {
    private final String email;

    public UserAlreadyRegisteredException(String email) {
        super("Такой пользователь уже зарегистрирован");
        this.email = email;
    }
}

