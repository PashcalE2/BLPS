package main.blps_lab1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
public class ServerController {
    @PostMapping(value = "/server/pay")
    public ResponseEntity<?> pay(
            @RequestParam String card_serial,
            @RequestParam String card_validity,
            @RequestParam String card_cvv,
            @RequestParam(defaultValue = "0") Integer money
    ) {
        return new ResponseEntity<>("Тест", HttpStatus.OK);
    }
}
