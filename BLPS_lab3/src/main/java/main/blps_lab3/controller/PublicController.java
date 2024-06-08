package main.blps_lab3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.dto.JwtRequest;
import main.blps_lab3.dto.JwtResponse;
import main.blps_lab3.dto.RefreshJwtRequest;
import main.blps_lab3.exception.*;
import main.blps_lab3.model.RoleEnum;
import main.blps_lab3.service.AuthService;
import main.blps_lab3.service.interfaces.CourseServiceInterface;
import main.blps_lab3.service.interfaces.UserServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    ) throws UserAlreadyRegisteredException, UserRegisterException {
        userService.registerUser(email, password, RoleEnum.CLIENT);
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws UserNotFoundException, UserWrongPasswordException, UserIsBannedException {
        JwtResponse jwtResponse = authService.login(jwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/access")
    public ResponseEntity<?> newAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) throws UserNotFoundException, TokenIsInvalidException, TokenIsExpiredException, TokenNotFoundException, TokenNotEqualsException, UserIsBannedException {
        JwtResponse jwtResponse = authService.getNewAccessToken(refreshJwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> newRefreshToken(@RequestBody RefreshJwtRequest refreshJwtRequest) throws UserNotFoundException, TokenIsInvalidException, TokenNotFoundException, TokenIsExpiredException, TokenNotEqualsException, UserIsBannedException {
        JwtResponse jwtResponse = authService.getNewRefreshToken(refreshJwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    @PreAuthorize(value = "hasAnyAuthority('CLIENT', 'ADMIN')")
    public ResponseEntity<?> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        authService.logout((String) auth.getPrincipal());

        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
