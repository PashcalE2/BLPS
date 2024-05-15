package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class ClientCardDataUpdateException extends Exception {
    private final Long userId;
    private final String cardSerial;
    private final String cardValidity;
    private final String cardCvv;

    public ClientCardDataUpdateException(Long userId, String cardSerial, String cardValidity, String cardCvv) {
        super("Неправильный формат данных карточки");
        this.userId = userId;
        this.cardSerial = cardSerial;
        this.cardValidity = cardValidity;
        this.cardCvv = cardCvv;
    }
}
