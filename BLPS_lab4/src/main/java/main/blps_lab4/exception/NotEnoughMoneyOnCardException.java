package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class NotEnoughMoneyOnCardException extends BaseException {
    private final Long cardId;
    private final Integer price;

    public NotEnoughMoneyOnCardException(Long cardId, Integer price) {
        super("Недостаточно средств на карте");
        this.cardId = cardId;
        this.price = price;
    }

}
