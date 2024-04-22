package main.blps_lab1.controller;

import main.blps_lab1.service.ServerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
public class ServerController {
    @Autowired
    private ServerServiceInterface serverService;

    @PostMapping(value = "/server/pay")
    public @ResponseBody ResponseEntity<?> pay(
            @RequestParam String card_serial,
            @RequestParam String card_validity,
            @RequestParam String card_cvv,
            @RequestParam(defaultValue = "0") Integer money
    ) {
        serverService.removeMoney(card_serial, card_validity, card_cvv, money);
        System.out.printf("Операция списания денег выполнена:\n%s\n%d\n", card_serial, money);
        return new ResponseEntity<>("Деньги списаны", HttpStatus.OK);
    }
}
