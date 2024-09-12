package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class TokenNotFoundException extends  BaseException {
    private final String login;
    private final String token;

    public TokenNotFoundException(String login, String token) {
        super("Токен не найден в хранилище");

        this.login = login;
        this.token = token;
    }
}
