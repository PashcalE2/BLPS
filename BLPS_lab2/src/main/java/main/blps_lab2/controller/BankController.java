package main.blps_lab2.controller;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.exception.NotEnoughMoneyOnCardException;
import main.blps_lab2.service.BankServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/bank")
@Slf4j
public class BankController {
    @Autowired
    private BankServiceInterface serverService;

    @PostMapping(value = "/pay")
    public @ResponseBody ResponseEntity<?> pay(
            @RequestParam(defaultValue = "0") Long cardId,
            @RequestParam(defaultValue = "0") Integer money
    ) throws NotEnoughMoneyOnCardException {
        try {
            serverService.removeMoney(cardId, money);
        }
        catch (RuntimeException e) {
            throw new NotEnoughMoneyOnCardException(cardId, money);
        }
        log.info(String.format("Операция списания денег выполнена:\n%d\n%d\n", cardId, money));
        return new ResponseEntity<>("Деньги списаны", HttpStatus.OK);
    }
}
