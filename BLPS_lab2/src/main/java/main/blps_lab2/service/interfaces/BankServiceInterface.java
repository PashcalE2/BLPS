package main.blps_lab2.service.interfaces;

import main.blps_lab2.exception.NotEnoughMoneyOnCardException;

public interface BankServiceInterface {
    void removeMoney(Long cardId, Integer money) throws NotEnoughMoneyOnCardException;
}
