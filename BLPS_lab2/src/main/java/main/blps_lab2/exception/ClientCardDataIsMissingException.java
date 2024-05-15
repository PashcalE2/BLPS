package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class ClientCardDataIsMissingException extends Exception {
    private final Long clientId;

    public ClientCardDataIsMissingException(Long clientId) {
        super("Нет данных для оплаты курса");
        this.clientId = clientId;
    }
}
