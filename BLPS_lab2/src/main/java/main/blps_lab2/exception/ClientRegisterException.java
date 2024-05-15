package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class ClientRegisterException extends Exception {
    private final String email;

    public ClientRegisterException(String email) {
        super("Некорректный формат почты");
        this.email = email;
    }
}
