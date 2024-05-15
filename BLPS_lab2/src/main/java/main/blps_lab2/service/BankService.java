package main.blps_lab2.service;

import main.blps_lab2.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService implements BankServiceInterface {
    @Autowired
    private BankRepository bankRepository;

    @Override
    public void removeMoney(Long cardId, Integer money) {
        bankRepository.removeMoney(cardId,  money);
    }
}
