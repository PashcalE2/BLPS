package main.blps_lab2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.dto.BankCardCredentials;
import main.blps_lab2.exception.*;
import main.blps_lab2.service.interfaces.ClientServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/client")
@PreAuthorize(value = "hasAnyAuthority('CLIENT', 'ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientServiceInterface clientService;

    @PostMapping(value = "/course_sign_up")
    public ResponseEntity<?> courseSignUp(
            @RequestParam(defaultValue = "0") Long courseId
    ) throws CourseNotFoundException, ClientAlreadySignedUpException, UserNotFoundException, ClientCardDataIsMissingException, CantRequestBankException, NotEnoughMoneyOnCardException, UserIsBannedException, CourseIsBlockedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        clientService.courseSignUp(auth, courseId);
        return new ResponseEntity<>("Пользователь записан на курс", HttpStatus.OK);
    }

    @PostMapping(value = "/attach_debit_card")
    public ResponseEntity<?> attachDebitCard(
            @RequestBody BankCardCredentials bankCard
    ) throws ClientCardDataUpdateException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        clientService.attachClientCard(auth, bankCard);
        return new ResponseEntity<>("Данные обновлены", HttpStatus.OK);
    }
}
