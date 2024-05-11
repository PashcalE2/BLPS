package main.blps_lab2.service;

import main.blps_lab2.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerService implements ServerServiceInterface {
    @Autowired
    private ServerRepository serverRepository;

    @Override
    public void removeMoney(String card_serial, String validity_date, String cvv, Integer money) {
        serverRepository.removeMoney(card_serial, validity_date, cvv, money);
    }
}
