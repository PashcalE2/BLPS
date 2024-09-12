package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class TokenIsInvalidException extends BaseException {
    private final String token;

    public TokenIsInvalidException(String token) {
        super("Некорректный токен");
        this.token = token;
    }
}
