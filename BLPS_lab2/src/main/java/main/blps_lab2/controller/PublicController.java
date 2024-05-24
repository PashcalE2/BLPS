package main.blps_lab2.controller;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.exception.ClientRegisterException;
import main.blps_lab2.service.interfaces.CourseServiceInterface;
import main.blps_lab2.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/public")
@Slf4j
public class PublicController {
    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private CourseServiceInterface courseService;

    @GetMapping(value = "/get_courses")
    public ResponseEntity<?> getCoursesByName(
            @RequestParam String name
    ) {
        return new ResponseEntity<>(courseService.getCoursesByName(name), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(
            @RequestParam String email,
            @RequestParam String password
    ) throws ClientRegisterException {

        try {
            userService.registerUser(email, password, RoleEnum.CLIENT);
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ClientRegisterException(email);
        }

        log.info(String.format("Зарегистрирован пользователь:\n%s\n%s\n", email, password));
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<?> login() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refresh() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
