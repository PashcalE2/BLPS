package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class TokenIsExpiredException extends BaseException {
    private final String token;

    public TokenIsExpiredException(String token) {
        super("Токен протух");
        this.token = token;
    }
}
