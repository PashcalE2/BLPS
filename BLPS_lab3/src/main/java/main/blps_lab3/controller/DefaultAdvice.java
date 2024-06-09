package main.blps_lab3.controller;

import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DefaultAdvice {

    // BANK

    @ExceptionHandler(CantRequestBankException.class)
    public ResponseEntity<?> handleCantRequestBank(CantRequestBankException e) {
        log.error(String.format("Невозможно отправить запрос в банк: \n%s \n", e.getError()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotEnoughMoneyOnCardException.class)
    public ResponseEntity<?> handleNotEnoughMoneyOnCard(NotEnoughMoneyOnCardException e) {
        log.error(String.format("Недостаточно средств на карте: \ncardId = %d \nprice = %d \n", e.getCardId(), e.getPrice()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // CLIENT

    @ExceptionHandler(ClientAlreadySignedUpException.class)
    public ResponseEntity<?> handleClientAlreadySignedUp(ClientAlreadySignedUpException e) {
        log.error(String.format("Пользователь уже записан на курс: \nuserId = %d \ncourseId = %d\n", e.getClientId(), e.getCourseId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientCardDataIsMissingException.class)
    public ResponseEntity<?> handleClientCardDataIsMissing(ClientCardDataIsMissingException e) {
        log.error(String.format("У пользователя нет реквизитов для оплаты: \n%s \n", e.getClientId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientCardDataUpdateException.class)
    public ResponseEntity<?> handleClientCardDataUpdate(ClientCardDataUpdateException e) {
        log.error(String.format("Неправильный формат данных карточки: \nid = %d \nserial = %s \ndate = %s \ncvv = %s \n",
                e.getUserId(),
                e.getBankCardCredentials().getSerialNumber(),
                e.getBankCardCredentials().getValidityDate(),
                e.getBankCardCredentials().getCvv()));

        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }


    // USER AUTH

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuth(AuthException e) {
        log.error("Ошибка auth*: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException e) {
        log.error(String.format("Нет такого пользователя: \nlogin = %s \n", e.getLogin()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException e) {
        log.error("Ошибка с JWT фильтром: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserWrongPasswordException.class)
    public ResponseEntity<?> handleUserWrongPassword(UserWrongPasswordException e) {
        log.error(String.format("Неверный пароль: \nlogin = %s \npassword = %s \n", e.getLogin(), e.getPassword()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserIsBannedException.class)
    public ResponseEntity<?> handleUserIsBanned(UserIsBannedException e) {
        log.error(String.format("Пользователь забанен: \nuserId = %d \n", e.getUserId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserRegisterException.class)
    public ResponseEntity<?> handleUserRegister(UserRegisterException e) {
        log.error(String.format("Некорректный формат почты: \nemail = %s \n", e.getEmail()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        log.error(String.format("Пользователь уже зарегистрирован: \nemail = %s \n", e.getEmail()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // // TOKEN

    @ExceptionHandler(TokenIsExpiredException.class)
    public ResponseEntity<?> handleTokenIsExpired(TokenIsExpiredException e) {
        log.error(String.format("Токен протух: \ntoken = %s \n", e.getToken()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenIsInvalidException.class)
    public ResponseEntity<?> handleTokenIsInvalid(TokenIsInvalidException e) {
        log.error(String.format("Некорректный токен: \ntoken = %s \n", e.getToken()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotEqualsException.class)
    public ResponseEntity<?> handleTokenNotEquals(TokenNotEqualsException e) {
        log.error(String.format("Полученный токен не соответствует токену в хранилище: \ngivenToken = %s \nexpectedToken = %s", e.getGivenToken(), e.getExpectedToken()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<?> handleTokenNotFound(TokenNotFoundException e) {
        log.error(String.format("Токен не найден в хранилище: \nlogin = %s \ntoken = %s", e.getLogin(), e.getToken()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // COURSE

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> handleCourseNotFound(CourseNotFoundException e) {
        log.error(String.format("Нет такого курса: \ncourseId = %d \n", e.getCourseId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CourseIsBlockedException.class)
    public ResponseEntity<?> handleCourseIsBlocked(CourseIsBlockedException e) {
        log.error(String.format("Этот курс заблокирован: \ncourseId = %d \n", e.getCourseId()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    // ADMIN

    @ExceptionHandler(UserAlreadyBannedExcpetion.class)
    public ResponseEntity<?> handleUserAlreadyBanned(UserAlreadyBannedExcpetion e) {
        log.error(String.format("Пользователь уже забанен: \nlogin = %s", e.getLogin()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyUnbannedExcpetion.class)
    public ResponseEntity<?> handleUserAlreadyUnbanned(UserAlreadyUnbannedExcpetion e) {
        log.error(String.format("Пользователь уже разбанен: \nlogin = %s", e.getLogin()));
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
