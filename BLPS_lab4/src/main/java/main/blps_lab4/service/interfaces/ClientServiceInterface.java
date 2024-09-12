package main.blps_lab4.service.interfaces;

import main.blps_lab4.dto.BankCardCredentials;
import main.blps_lab4.exception.*;
import org.springframework.security.core.Authentication;

public interface ClientServiceInterface {
    void courseSignUp(String accessToken, Long courseId) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException, UserNotFoundException, UserIsBannedException, CourseIsBlockedException;

    void attachClientCard(String accessToken, BankCardCredentials bankCardCredentials) throws ClientCardDataUpdateException, UserNotFoundException;
}
