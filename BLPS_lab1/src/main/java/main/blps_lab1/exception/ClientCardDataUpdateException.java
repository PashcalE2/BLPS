package main.blps_lab1.exception;

public class ClientCardDataUpdateException extends Exception {
    private final String email;
    private final String password;
    private final String card_serial;
    private final String card_validity;
    private final String card_cvv;

    public ClientCardDataUpdateException(String email, String password, String card_serial, String card_validity, String card_cvv) {
        super("Неправильный формат данных карточки");
        this.email = email;
        this.password = password;
        this.card_serial = card_serial;
        this.card_validity = card_validity;
        this.card_cvv = card_cvv;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCardSerial() {
        return card_serial;
    }

    public String getCardValidity() {
        return card_validity;
    }

    public String getCardCvv() {
        return card_cvv;
    }
}
