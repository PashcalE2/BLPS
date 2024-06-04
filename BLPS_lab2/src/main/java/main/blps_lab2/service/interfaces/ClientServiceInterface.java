package main.blps_lab2.service.interfaces;

import main.blps_lab2.dto.BankCardCredentials;
import main.blps_lab2.exception.*;
import org.springframework.security.core.Authentication;

public interface ClientServiceInterface {
    void courseSignUp(Authentication auth, Long courseId) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException, UserNotFoundException, UserIsBannedException;

    void attachClientCard(Authentication auth, BankCardCredentials bankCardCredentials) throws ClientCardDataUpdateException, UserNotFoundException;
}
