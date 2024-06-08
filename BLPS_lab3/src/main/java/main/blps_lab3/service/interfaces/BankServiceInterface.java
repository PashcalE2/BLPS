package main.blps_lab3.service.interfaces;

import main.blps_lab3.exception.NotEnoughMoneyOnCardException;

public interface BankServiceInterface {
    void removeMoney(Long cardId, Integer money) throws NotEnoughMoneyOnCardException;
}
