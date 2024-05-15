package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class ClientCardDataUpdateException extends Exception {
    private final String email;
    private final String cardSerial;
    private final String cardValidity;
    private final String cardCvv;

    public ClientCardDataUpdateException(String email, String cardSerial, String cardValidity, String cardCvv) {
        super("Неправильный формат данных карточки");
        this.email = email;
        this.cardSerial = cardSerial;
        this.cardValidity = cardValidity;
        this.cardCvv = cardCvv;
    }
}
