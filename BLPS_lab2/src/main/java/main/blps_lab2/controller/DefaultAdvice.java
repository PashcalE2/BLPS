package main.blps_lab2.controller;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DefaultAdvice {
    @ExceptionHandler(CantRequestBankException.class)
    public ResponseEntity<?> handleCantRequestBank(CantRequestBankException e) {
        log.error(String.format("Невозможно отправить запрос в банк: %s", e.getError()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ClientAlreadySignedUpException.class)
    public ResponseEntity<?> handleClientAlreadySignedUp(ClientAlreadySignedUpException e) {
        log.error(String.format("Пользователь (%d) уже записан на курс (%d)", e.getClientId(), e.getCourseId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientCardDataIsMissingException.class)
    public ResponseEntity<?> handleClientCardDataIsMissing(ClientCardDataIsMissingException e) {
        log.error(String.format("У пользователя (%d) нет реквизитов для оплаты\n", e.getClientId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientCardDataUpdateException.class)
    public ResponseEntity<?> handleClientCardDataUpdate(ClientCardDataUpdateException e) {
        log.error(String.format("Неправильный формат данных карточки: \n%d \n%s \n%s \n%s",
                e.getUserId(),
                e.getCardSerial(),
                e.getCardValidity(),
                e.getCardCvv()));

        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<?> handleClientNotFound(ClientNotFoundException e) {
        log.error(String.format("Нет такого пользователя (%s, %s)", e.getLogin(), e.getPassword()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientRegisterException.class)
    public ResponseEntity<?> handleClientRegister(ClientRegisterException e) {
        log.error(String.format("Некорректный формат почты (%s)", e.getEmail()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> handleCourseNotFound(CourseNotFoundException e) {
        log.error(String.format("Нет такого курса (%d)", e.getCourseId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotEnoughMoneyOnCardException.class)
    public ResponseEntity<?> handleNotEnoughMoneyOnCard(NotEnoughMoneyOnCardException e) {
        log.error(String.format("На карте '%s' должно быть хотя бы %d средств", e.getCardId(), e.getPrice()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
