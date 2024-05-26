package main.blps_lab2.controller.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.Course;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.exception.*;
import main.blps_lab2.service.ClientService;
import main.blps_lab2.service.CourseService;
import main.blps_lab2.service.UserService;
import main.blps_lab2.utils.BankRequests;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientHandler {
    // Норм идея, перенести логику сюда, убрать Service
    private final CourseService courseService;
    private final UserService userService;
    private final ClientService clientService;

    public void courseSignUp(String email, String password, Long courseId) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientNotFoundException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException {
        Optional<Course> db_course = courseService.getCourseById(courseId);
        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        Course course = db_course.get();

        Optional<UserEntity> db_client = userService.findByEmail(email);
        if (db_client.isEmpty()) {
            throw new ClientNotFoundException(email, password);
        }
        UserEntity client = db_client.get();

        if (clientService.isClientSignedUpForCourse(client.getId(), course.getId())) {
            throw new ClientAlreadySignedUpException(client.getId(), course.getId());
        }

        Optional<BankCard> db_bankCard = clientService.findBankCardByClientId(client.getId());
        if (db_bankCard.isEmpty()) {
            throw new ClientCardDataIsMissingException(client.getId());
        }
        BankCard bankCard = db_bankCard.get();

        BankRequests.removeMoney(bankCard, course);

        clientService.courseSignUp(client.getId(), course.getId());
        log.info(String.format("Пользователь (%d) записался на курс (%d)\n", client.getId(), courseId));
    }
}
