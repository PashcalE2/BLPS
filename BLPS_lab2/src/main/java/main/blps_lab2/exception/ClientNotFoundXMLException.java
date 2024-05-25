package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class ClientNotFoundXMLException extends Exception {
    private final String login;

    public ClientNotFoundXMLException(String login) {
        super("Неправильные данные пользователя");
        this.login = login;
    }
}
