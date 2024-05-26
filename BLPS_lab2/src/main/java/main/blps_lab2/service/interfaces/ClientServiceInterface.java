package main.blps_lab2.service.interfaces;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.exception.ClientCardDataUpdateException;

import java.util.Optional;

public interface ClientServiceInterface {
    Optional<BankCard> findBankCardByClientId(Long clientId);

    void attachClientCard(Long userId, String serialNumber, String validityDate, String cvv) throws ClientCardDataUpdateException;

    void courseSignUp(Long clientId, Long courseId);

    Boolean isClientSignedUpForCourse(Long clientId, Long courseId);
}
