package main.blps_lab1.service;

import main.blps_lab1.repository.ServerRepository;
import org.springframework.stereotype.Service;

@Service
public class ServerService implements ServerRepository {
    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void removeMoney(String card_serial, String validity_date, String cvv, Integer money) {
        serverRepository.removeMoney(card_serial, validity_date, cvv, money);
    }
}
