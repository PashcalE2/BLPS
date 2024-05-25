package main.blps_lab2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.exception.NotEnoughMoneyOnCardException;
import main.blps_lab2.service.interfaces.BankServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/bank")
@RequiredArgsConstructor
@Slf4j
public class BankController {
    private final BankServiceInterface bankService;

    @PostMapping(value = "/pay")
    public ResponseEntity<?> pay(
            @RequestParam(defaultValue = "0") Long cardId,
            @RequestParam(defaultValue = "0") Integer money
    ) throws NotEnoughMoneyOnCardException {
        bankService.removeMoney(cardId, money);
        return new ResponseEntity<>("Деньги списаны", HttpStatus.OK);
    }
}
