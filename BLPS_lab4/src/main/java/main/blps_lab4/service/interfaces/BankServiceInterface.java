package main.blps_lab4.service.interfaces;

import main.blps_lab4.exception.NotEnoughMoneyOnCardException;

public interface BankServiceInterface {
    void removeMoney(Long cardId, Integer money) throws NotEnoughMoneyOnCardException;
}
