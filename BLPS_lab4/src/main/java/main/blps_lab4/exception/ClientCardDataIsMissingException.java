package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class ClientCardDataIsMissingException extends BaseException {
    private final Long clientId;

    public ClientCardDataIsMissingException(Long clientId) {
        super("Нет данных для оплаты курса");
        this.clientId = clientId;
    }
}
