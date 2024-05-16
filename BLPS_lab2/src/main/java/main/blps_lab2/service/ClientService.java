package main.blps_lab2.service;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.service.interfaces.ClientServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService implements ClientServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<BankCard> findBankCardByClientId(Long clientId) {
        return userRepository.findBankCardByUserId(clientId);
    }

    @Override
    public void attachClientCard(Long userId, String serialNumber, String validityDate, String cvv) {
        userRepository.attachClientCard(userId, serialNumber, validityDate, cvv);
    }

    @Override
    public void courseSignUp(Long clientId, Long courseId) {
        userRepository.courseSignUp(clientId, courseId);
    }

    @Override
    public Boolean isClientSignedUpForCourse(Long clientId, Long courseId) {
        return userRepository.isClientSignedUpForCourse(clientId, courseId);
    }
}
