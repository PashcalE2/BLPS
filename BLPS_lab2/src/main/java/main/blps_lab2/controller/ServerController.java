package main.blps_lab2.controller;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.exception.NotEnoughMoneyOnCardException;
import main.blps_lab2.service.ServerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/server")
@Slf4j
public class ServerController {
    @Autowired
    private ServerServiceInterface serverService;

    @PostMapping(value = "/pay")
    public @ResponseBody ResponseEntity<?> pay(
            @RequestParam String card_serial,
            @RequestParam String card_validity,
            @RequestParam String card_cvv,
            @RequestParam(defaultValue = "0") Integer money
    ) throws NotEnoughMoneyOnCardException {
        try {
            serverService.removeMoney(card_serial, card_validity, card_cvv, money);
        }
        catch (RuntimeException e) {
            throw new NotEnoughMoneyOnCardException(card_serial, money);
        }
        log.info(String.format("Операция списания денег выполнена:\n%s\n%d\n", card_serial, money));
        return new ResponseEntity<>("Деньги списаны", HttpStatus.OK);
    }
}
