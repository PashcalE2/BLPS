package main.blps_lab2.controller;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.exception.ClientAlreadyRegisteredException;
import main.blps_lab2.exception.ClientRegisterException;
import main.blps_lab2.security.JwtRequest;
import main.blps_lab2.security.JwtResponse;
import main.blps_lab2.security.RefreshJwtRequest;
import main.blps_lab2.service.AuthService;
import main.blps_lab2.service.interfaces.CourseServiceInterface;
import main.blps_lab2.service.interfaces.UserServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@CrossOrigin
@ApplicationScope
@RequestMapping(value = "/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {
    private final AuthService authService;
    private final UserServiceInterface userService;
    private final CourseServiceInterface courseService;

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
    ) throws ClientAlreadyRegisteredException, ClientRegisterException {
        userService.registerUser(email, password, RoleEnum.CLIENT);
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws AuthException {
        JwtResponse jwtResponse = authService.login(jwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/access")
    public ResponseEntity<?> newAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) throws AuthException {
        JwtResponse jwtResponse = authService.getNewAccessToken(refreshJwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> newRefreshToken(@RequestBody RefreshJwtRequest refreshJwtRequest) throws AuthException {
        JwtResponse jwtResponse = authService.getNewRefreshToken(refreshJwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
