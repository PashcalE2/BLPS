package main.blps_lab3.service.interfaces;

import main.blps_lab3.dto.BankCardCredentials;
import main.blps_lab3.exception.*;
import org.springframework.security.core.Authentication;

public interface ClientServiceInterface {
    void courseSignUp(Authentication auth, Long courseId) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException, UserNotFoundException, UserIsBannedException, CourseIsBlockedException;

    void attachClientCard(Authentication auth, BankCardCredentials bankCardCredentials) throws ClientCardDataUpdateException, UserNotFoundException;
}
