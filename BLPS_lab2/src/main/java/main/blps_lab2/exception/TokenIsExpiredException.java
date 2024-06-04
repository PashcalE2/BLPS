package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class TokenIsExpiredException extends Exception {
    private final String token;

    public TokenIsExpiredException(String token) {
        super("Токен протух");
        this.token = token;
    }
}
