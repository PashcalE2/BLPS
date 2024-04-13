package main.blps_lab1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.ApplicationScope;

@Controller
@CrossOrigin
@ApplicationScope
public class ClientController {
    @GetMapping(value = "/client/test")
    public @ResponseBody ResponseEntity<?> test() {
        return new ResponseEntity<>("Тест", HttpStatus.OK);
    }
}
