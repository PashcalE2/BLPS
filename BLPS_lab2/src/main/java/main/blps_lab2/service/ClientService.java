package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.dto.BankCardCredentials;
import main.blps_lab2.model.BankCard;
import main.blps_lab2.model.Course;
import main.blps_lab2.model.UserEntity;
import main.blps_lab2.exception.*;
import main.blps_lab2.repository.CourseRepository;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.repository.UsersCoursesRepository;
import main.blps_lab2.service.interfaces.ClientServiceInterface;
import main.blps_lab2.utils.BankRequests;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements ClientServiceInterface {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UsersCoursesRepository usersCoursesRepository;

    @Override
    public void attachClientCard(Authentication auth, BankCardCredentials bankCardCredentials) throws ClientCardDataUpdateException, UserNotFoundException {
        String login = (String) auth.getPrincipal();
        Optional<UserEntity> db_client = userRepository.findByEmail(login);
        if (db_client.isEmpty()) {
            throw new UserNotFoundException(login);
        }
        UserEntity client = db_client.get();

        try {
            userRepository.attachClientCard(client.getId(), bankCardCredentials.getSerialNumber(), bankCardCredentials.getValidityDate(), bankCardCredentials.getCvv());
        }
        catch (RuntimeException e) {
            throw new ClientCardDataUpdateException(client.getId(), bankCardCredentials);
        }

        log.info(String.format("Данные карты клиента (%d) обновлены:\n%s\n%s\n%s\n", client.getId(), bankCardCredentials.getSerialNumber(), bankCardCredentials.getValidityDate(), bankCardCredentials.getCvv()));

    }

    @Override
    public void courseSignUp(Authentication auth, Long courseId) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException, UserNotFoundException, UserIsBannedException, CourseIsBlockedException {
        Optional<Course> db_course = courseRepository.getCourseById(courseId);
        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        Course course = db_course.get();

        if (course.getBlocked()) {
            throw new CourseIsBlockedException(courseId);
        }

        String login = (String) auth.getPrincipal();
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

        Optional<BankCard> db_bankCard = userRepository.findBankCardByUserId(client.getId());
        if (db_bankCard.isEmpty()) {
            throw new ClientCardDataIsMissingException(client.getId());
        }
        BankCard bankCard = db_bankCard.get();

        BankRequests.removeMoney(bankCard, course);

        usersCoursesRepository.courseSignUp(client.getId(), course.getId());
        log.info(String.format("Пользователь (%d) записался на курс (%d)\n", client.getId(), courseId));
    }
}
