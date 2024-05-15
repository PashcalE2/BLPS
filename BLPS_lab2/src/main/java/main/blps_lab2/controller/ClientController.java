package main.blps_lab2.controller;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.User;
import main.blps_lab2.data.CourseInterface;
import main.blps_lab2.exception.*;
import main.blps_lab2.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/client")
@Slf4j
public class ClientController {
    @Autowired
    private UserServiceInterface userService;

    @PostMapping(value = "/course_sign_up")
    public @ResponseBody ResponseEntity<?> courseSignUp(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Long course_id
    ) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientNotFoundException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException {
        // вынести во второй лабе

        Optional<CourseInterface> db_course = userService.getCourseById(course_id);
        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(course_id);
        }
        CourseInterface course = db_course.get();

        Optional<User> db_client = userService.findUserByEmailAndPassword(email, password);
        if (db_client.isEmpty()) {
            throw new ClientNotFoundException(email, password);
        }
        User client = db_client.get();

        if (userService.isUserSignedUpForCourse(client.getId(), course.getId())) {
            throw new ClientAlreadySignedUpException(client.getId(), course.getId());
        }

        Optional<BankCard> db_bankCard = userService.findBankCardByUserId(client.getId());
        if (db_bankCard.isEmpty()) {
            throw new ClientCardDataIsMissingException(client.getId());
        }
        BankCard bankCard = db_bankCard.get();

        String bankRequest = String.format("http://localhost:22600/server/pay?card_serial=%s&card_validity=%s&card_cvv=%s&money=%s",
                URLEncoder.encode(bankCard.getSerialNumber(), StandardCharsets.UTF_8),
                URLEncoder.encode(bankCard.getValidityDate(), StandardCharsets.UTF_8),
                URLEncoder.encode(bankCard.getCvv(), StandardCharsets.UTF_8),
                URLEncoder.encode(course.getPrice().toString(), StandardCharsets.UTF_8)
        );

        HttpResponse<String> response;
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(bankRequest))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new CantRequestBankException(e.getMessage());
        }

        if (HttpStatus.resolve(response.statusCode()) != HttpStatus.OK) {
            throw new NotEnoughMoneyOnCardException(bankCard.getId(), course.getPrice());
        }

        userService.courseSignUp(client.getId(), course.getId());
        log.info(String.format("Пользователь (%d) записался на курс (%d)\n", client.getId(), course_id));
        return new ResponseEntity<>("Пользователь записан на курс", HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public @ResponseBody ResponseEntity<?> clientRegister(
            @RequestParam String email,
            @RequestParam String password
    ) throws ClientRegisterException {

        try {
            userService.registerUser(email, password, RoleEnum.CLIENT);
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ClientRegisterException(email);
        }

        log.info(String.format("Зарегистрирован пользователь:\n%s\n%s\n", email, password));
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    @PostMapping(value = "/attach_debit_card")
    public @ResponseBody ResponseEntity<?> attachDebitCard(
            @RequestParam(defaultValue = "0") Long userId,
            @RequestParam String cardSerial,
            @RequestParam String cardValidityDate,
            @RequestParam String cardCvv
    ) throws ClientCardDataUpdateException {

        try {
            userService.attachClientCard(userId, cardSerial, cardValidityDate, cardCvv);
        }
        catch (RuntimeException e) {
            throw new ClientCardDataUpdateException(userId, cardSerial, cardValidityDate, cardCvv);
        }

        log.info(String.format("Данные карты клиента (%d) обновлены:\n%s\n%s\n%s\n", userId, cardSerial, cardValidityDate, cardCvv));
        return new ResponseEntity<>("Данные обновлены", HttpStatus.OK);
    }

    @GetMapping(value = "/get_courses")
    public @ResponseBody ResponseEntity<?> getCoursesByName(
            @RequestParam String name
    ) {
        return new ResponseEntity<>(userService.getCoursesByName(name), HttpStatus.OK);
    }
}
