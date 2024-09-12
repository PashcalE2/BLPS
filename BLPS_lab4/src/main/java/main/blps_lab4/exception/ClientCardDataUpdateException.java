package main.blps_lab4.exception;

import lombok.Getter;
import main.blps_lab4.dto.BankCardCredentials;

@Getter
public class ClientCardDataUpdateException extends BaseException {
    private final Long userId;
    private final BankCardCredentials bankCardCredentials;

    public ClientCardDataUpdateException(Long userId, BankCardCredentials bankCardCredentials) {
        super("Неправильный формат данных карточки");
        this.userId = userId;
        this.bankCardCredentials = bankCardCredentials;
    }
}
