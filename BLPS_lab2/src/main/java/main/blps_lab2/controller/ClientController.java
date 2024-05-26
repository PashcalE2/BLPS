package main.blps_lab2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.controller.handler.ClientHandler;
import main.blps_lab2.exception.*;
import main.blps_lab2.service.interfaces.ClientServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientHandler clientHandler;
    private final ClientServiceInterface clientService;

    @PostMapping(value = "/course_sign_up")
    public ResponseEntity<?> courseSignUp(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Long courseId
    ) throws CourseNotFoundException, ClientAlreadySignedUpException, ClientNotFoundException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException {
        clientHandler.courseSignUp(email, password, courseId);
        return new ResponseEntity<>("Пользователь записан на курс", HttpStatus.OK);
    }

    @PostMapping(value = "/attach_debit_card")
    public ResponseEntity<?> attachDebitCard(
            @RequestParam(defaultValue = "0") Long userId,
            @RequestParam String cardSerial,
            @RequestParam String cardValidityDate,
            @RequestParam String cardCvv
    ) throws ClientCardDataUpdateException {
        clientService.attachClientCard(userId, cardSerial, cardValidityDate, cardCvv);
        return new ResponseEntity<>("Данные обновлены", HttpStatus.OK);
    }

    @GetMapping(value = "/nothing")
    public void nothing() {}
}
