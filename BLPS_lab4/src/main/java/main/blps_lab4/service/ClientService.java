package main.blps_lab4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.dto.BankCardCredentials;
import main.blps_lab4.dto.BankCardInterface;
import main.blps_lab4.exception.*;
import main.blps_lab4.model.Course;
import main.blps_lab4.model.UserEntity;
import main.blps_lab4.repository.CourseRepository;
import main.blps_lab4.repository.UserRepository;
import main.blps_lab4.repository.UsersCoursesRepository;
import main.blps_lab4.service.interfaces.ClientServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements ClientServiceInterface {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UsersCoursesRepository usersCoursesRepository;
    private final AuthService authService;

    @Override
    public void attachClientCard(String accessToken, BankCardCredentials bankCardCredentials) throws ClientCardDataUpdateException, UserNotFoundException {
        String login = authService.getLoginFromAccessToken(accessToken);
        Optional<UserEntity> db_client = userRepository.findByEmail(login);
        if (db_client.isEmpty()) {
            throw new UserNotFoundException(login);
        }
        UserEntity client = db_client.get();

        try {
            userRepository.attachClientCard(client.getId(), bankCardCredentials.getSerialNumber(), bankCardCredentials.getValidityDate(), bankCardCredentials.getCvv());
        }
        catch (Exception e) {
            throw new ClientCardDataUpdateException(client.getId(), bankCardCredentials);
        }

        log.info(String.format("Данные карты клиента (%d) обновлены:\n%s\n%s\n%s\n", client.getId(), bankCardCredentials.getSerialNumber(), bankCardCredentials.getValidityDate(), bankCardCredentials.getCvv()));

    }

    @Override
    public void courseSignUp(String accessToken, Long courseId) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException, UserNotFoundException, UserIsBannedException, CourseIsBlockedException {
        Optional<Course> db_course = courseRepository.getCourseById(courseId);
        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        Course course = db_course.get();

        if (course.getBlocked()) {
            throw new CourseIsBlockedException(courseId);
        }

        String login = authService.getLoginFromAccessToken(accessToken);
        Optional<UserEntity> db_client = userRepository.findByEmail(login);
        if (db_client.isEmpty()) {
            throw new UserNotFoundException(login);
        }
        UserEntity client = db_client.get();

        if (client.getBanned()) {
            throw new UserIsBannedException(client.getId());
        }

        if (usersCoursesRepository.isClientSignedUpForCourse(client.getId(), course.getId())) {
            throw new ClientAlreadySignedUpException(client.getId(), course.getId());
        }

        Optional<BankCardInterface> db_bankCard = userRepository.findBankCardByUserId(client.getId());
        if (db_bankCard.isEmpty()) {
            throw new ClientCardDataIsMissingException(client.getId());
        }
        BankCardInterface bankCard = db_bankCard.get();

        // BankRequests.removeMoney(bankCard, course);

        usersCoursesRepository.courseSignUp(client.getId(), course.getId());
        log.info(String.format("Пользователь (%d) записался на курс (%d)\n", client.getId(), courseId));
    }
}
