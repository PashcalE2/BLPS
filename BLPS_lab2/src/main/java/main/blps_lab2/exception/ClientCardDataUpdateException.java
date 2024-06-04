package main.blps_lab2.exception;

import lombok.Getter;
import main.blps_lab2.dto.BankCardCredentials;

@Getter
public class ClientCardDataUpdateException extends Exception {
    private final Long userId;
    private final BankCardCredentials bankCardCredentials;

    public ClientCardDataUpdateException(Long userId, BankCardCredentials bankCardCredentials) {
        super("Неправильный формат данных карточки");
        this.userId = userId;
        this.bankCardCredentials = bankCardCredentials;
    }
}
