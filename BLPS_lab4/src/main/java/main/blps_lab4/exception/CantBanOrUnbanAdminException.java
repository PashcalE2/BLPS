package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class CantBanOrUnbanAdminException extends BaseException {
    private final String login;

    public CantBanOrUnbanAdminException(String login) {
        super("Нельзя забанить или разбанить админа");
        this.login = login;
    }
}
