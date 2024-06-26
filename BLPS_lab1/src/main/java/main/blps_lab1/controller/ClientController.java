package main.blps_lab1.controller;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab1.data.ClientInterface;
import main.blps_lab1.data.CourseInterface;
import main.blps_lab1.exception.*;
import main.blps_lab1.service.ClientServiceInterface;
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
@Slf4j
public class ClientController {
    @Autowired
    private ClientServiceInterface clientService;

    @PostMapping(value = "/client/course_sign_up")
    public @ResponseBody ResponseEntity<?> courseSignUp(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Long course_id
    ) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientNotFoundException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException {
        // вынести во второй лабе

        Optional<CourseInterface> db_course = clientService.getCourseById(course_id);
        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(course_id);
        }
        CourseInterface course = db_course.get();

        Optional<ClientInterface> db_client = clientService.findClientByEmailAndPassword(email, password);
        if (db_client.isEmpty()) {
            throw new ClientNotFoundException(email, password);
        }
        ClientInterface client = db_client.get();

        if (clientService.isClientSignedUpForCourse(client.getId(), course.getId())) {
            throw new ClientAlreadySignedUpException(client.getId(), course.getId());
        }

        if (client.getCardSerial() == null || client.getCardValidity() == null || client.getCardCvv() == null) {
            throw new ClientCardDataIsMissingException(client.getId());
        }

        String bankRequest = String.format("http://localhost:22600/server/pay?card_serial=%s&card_validity=%s&card_cvv=%s&money=%s",
                URLEncoder.encode(client.getCardSerial(), StandardCharsets.UTF_8),
                URLEncoder.encode(client.getCardValidity(), StandardCharsets.UTF_8),
                URLEncoder.encode(client.getCardCvv(), StandardCharsets.UTF_8),
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
            throw new NotEnoughMoneyOnCardException(client.getCardSerial(), course.getPrice());
        }

        clientService.courseSignUp(client.getId(), course.getId());
        log.info(String.format("Пользователь (%d) записался на курс (%d)\n", client.getId(), course_id));
        return new ResponseEntity<>("Пользователь записан на курс", HttpStatus.OK);
    }

    @PostMapping(value = "/client/register")
    public @ResponseBody ResponseEntity<?> clientRegister(
            @RequestParam String email,
            @RequestParam String password
    ) throws ClientRegisterException {

        try {
            clientService.registerClient(email, password);
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ClientRegisterException(email);
        }

        log.info(String.format("Зарегистрирован пользователь:\n%s\n%s\n", email, password));
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    @PostMapping(value = "/client/set_debit_card")
    public @ResponseBody ResponseEntity<?> setDebitCard(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String card_serial,
            @RequestParam String card_validity,
            @RequestParam String card_cvv
    ) throws ClientCardDataUpdateException {

        try {
            clientService.updateClientCard(email, password, card_serial, card_validity, card_cvv);
        }
        catch (RuntimeException e) {
            throw new ClientCardDataUpdateException(email, password, card_serial, card_validity, card_cvv);
        }

        log.info(String.format("Данные карты клиента (%s) обновлены:\n%s\n%s\n%s\n", email, card_serial, card_validity, card_cvv));
        return new ResponseEntity<>("Данные обновлены", HttpStatus.OK);
    }

    @GetMapping(value = "/client/get_courses")
    public @ResponseBody ResponseEntity<?> getCoursesByName(
            @RequestParam String name
    ) {
        return new ResponseEntity<>(clientService.getCoursesByName(name), HttpStatus.OK);
    }
}
