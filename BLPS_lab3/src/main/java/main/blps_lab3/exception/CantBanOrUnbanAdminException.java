package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class CantBanOrUnbanAdminException extends Exception {
    private final String login;

    public CantBanOrUnbanAdminException(String login) {
        super("Нельзя забанить или разбанить админа");
        this.login = login;
    }
}
