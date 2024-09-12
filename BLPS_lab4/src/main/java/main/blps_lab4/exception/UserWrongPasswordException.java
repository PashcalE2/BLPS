package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class UserWrongPasswordException extends BaseException {
    private final String login;
    private final String password;

    public UserWrongPasswordException(String login, String password) {
        super("Неправильный пароль");
        this.login = login;
        this.password = password;
    }
}
