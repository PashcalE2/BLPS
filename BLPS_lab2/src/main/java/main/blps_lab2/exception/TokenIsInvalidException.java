package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class TokenIsInvalidException extends Exception {
    private final String token;

    public TokenIsInvalidException(String token) {
        super("Некорректный токен");
        this.token = token;
    }
}
