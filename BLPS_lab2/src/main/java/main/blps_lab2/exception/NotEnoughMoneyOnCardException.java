package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class NotEnoughMoneyOnCardException extends Exception {
    private final Long cardSerial;
    private final Integer price;

    public NotEnoughMoneyOnCardException(Long cardSerial, Integer price) {
        super("Недостаточно средств на карте");
        this.cardSerial = cardSerial;
        this.price = price;
    }
}
