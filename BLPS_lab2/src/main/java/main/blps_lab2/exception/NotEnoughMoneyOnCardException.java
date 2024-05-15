package main.blps_lab2.exception;

public class NotEnoughMoneyOnCardException extends Exception {
    private final Long cardSerial;
    private final Integer price;

    public NotEnoughMoneyOnCardException(Long cardSerial, Integer price) {
        super("Недостаточно средств на карте");
        this.cardSerial = cardSerial;
        this.price = price;
    }

    public Long getCardSerial() {
        return cardSerial;
    }

    public Integer getPrice() {
        return price;
    }
}
