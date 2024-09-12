package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class TokenNotEqualsException extends BaseException {
    private final String givenToken;
    private final String expectedToken;

    public TokenNotEqualsException(String givenToken, String expectedToken) {
        super("Полученный токен не соответствует токену в хранилище");

        this.givenToken = givenToken;
        this.expectedToken = expectedToken;
    }
}
