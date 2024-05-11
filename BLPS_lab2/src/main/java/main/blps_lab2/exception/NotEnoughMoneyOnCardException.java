package main.blps_lab2.exception;

public class NotEnoughMoneyOnCardException extends Exception {
    private final String card_serial;
    private final Integer price;

    public NotEnoughMoneyOnCardException(String card_serial, Integer price) {
        super("Недостаточно средств на карте");
        this.card_serial = card_serial;
        this.price = price;
    }

    public String getCardSerial() {
        return card_serial;
    }

    public Integer getPrice() {
        return price;
    }
}
