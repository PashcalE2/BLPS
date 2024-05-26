package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.BankCard;
import main.blps_lab2.exception.ClientCardDataUpdateException;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.repository.UsersCoursesRepository;
import main.blps_lab2.service.interfaces.ClientServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements ClientServiceInterface {
    private final UserRepository userRepository;
    private final UsersCoursesRepository usersCoursesRepository;

    @Override
    public Optional<BankCard> findBankCardByClientId(Long clientId) {
        return userRepository.findBankCardByUserId(clientId);
    }

    @Override
    public void attachClientCard(Long userId, String serialNumber, String validityDate, String cvv) throws ClientCardDataUpdateException {
        try {
            userRepository.attachClientCard(userId, serialNumber, validityDate, cvv);
        }
        catch (RuntimeException e) {
            throw new ClientCardDataUpdateException(userId, serialNumber, validityDate, cvv);
        }

        log.info(String.format("Данные карты клиента (%d) обновлены:\n%s\n%s\n%s\n", userId, serialNumber, validityDate, cvv));

    }

    @Override
    public void courseSignUp(Long clientId, Long courseId) {
        usersCoursesRepository.courseSignUp(clientId, courseId);
    }

    @Override
    public Boolean isClientSignedUpForCourse(Long clientId, Long courseId) {
        return usersCoursesRepository.isClientSignedUpForCourse(clientId, courseId);
    }
}
