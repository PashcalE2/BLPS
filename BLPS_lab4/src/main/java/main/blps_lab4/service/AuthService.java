package main.blps_lab4.service;


import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.dto.JwtRequest;
import main.blps_lab4.dto.JwtResponse;
import main.blps_lab4.dto.RefreshJwtRequest;
import main.blps_lab4.exception.*;
import main.blps_lab4.model.RoleEnum;
import main.blps_lab4.model.UserEntity;
import main.blps_lab4.security.MainPasswordEncoder;
import main.blps_lab4.security.jwt.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtUtils jwtUtils;
    private final MainPasswordEncoder passwordEncoder = new MainPasswordEncoder();

    public JwtResponse login(@NonNull JwtRequest authRequest) throws UserNotFoundException, UserWrongPasswordException, UserIsBannedException {
        UserEntity user = userService.findByEmail(authRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(authRequest.getLogin()));

        if (user.getBanned()) {
            throw new UserIsBannedException(user.getId());
        }

        log.info(String.format("Проверка пароля: %s", passwordEncoder.encode(authRequest.getPassword())));

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtUtils.generateAccessToken(user);
            final String refreshToken = jwtUtils.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new UserWrongPasswordException(authRequest.getLogin(), authRequest.getPassword());
        }
    }

    public void logout(String accessToken) throws TokenIsExpiredException {
        if (jwtUtils.validateAccessToken(accessToken)) {
            final Claims claims = jwtUtils.getAccessClaims(accessToken);
            final String login = claims.getSubject();

            refreshStorage.remove(login);
        }
    }

    public JwtResponse getNewAccessToken(@NonNull RefreshJwtRequest refreshJwtRequest) throws UserNotFoundException, TokenNotEqualsException, TokenIsExpiredException, TokenNotFoundException, TokenIsInvalidException, UserIsBannedException {
        String refreshToken = refreshJwtRequest.getRefreshToken();

        if (jwtUtils.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtUtils.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);

            if (savedRefreshToken == null) {
                throw new TokenNotFoundException(login, refreshToken);
            }
            else if (savedRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.findByEmail(login)
                        .orElseThrow(() -> new UserNotFoundException(login));

                if (user.getBanned()) {
                    throw new UserIsBannedException(user.getId());
                }

                final String accessToken = jwtUtils.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
            else {
                throw new TokenNotEqualsException(refreshToken, savedRefreshToken);
            }
        }

        throw new TokenIsInvalidException(refreshToken);
    }

    public JwtResponse getNewRefreshToken(@NonNull RefreshJwtRequest refreshJwtRequest) throws UserNotFoundException, TokenIsInvalidException, TokenNotFoundException, TokenIsExpiredException, TokenNotEqualsException, UserIsBannedException {
        String refreshToken = refreshJwtRequest.getRefreshToken();

        if (jwtUtils.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtUtils.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);

            if (savedRefreshToken == null) {
                throw new TokenNotFoundException(login, refreshToken);
            }
            else if (savedRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.findByEmail(login)
                        .orElseThrow(() -> new UserNotFoundException(login));

                if (user.getBanned()) {
                    throw new UserIsBannedException(user.getId());
                }

                final String accessToken = jwtUtils.generateAccessToken(user);
                final String newRefreshToken = jwtUtils.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
            else {
                throw new TokenNotEqualsException(refreshToken, savedRefreshToken);
            }
        }

        throw new TokenIsInvalidException(refreshToken);
    }

    public void throwIfWrongRole(RoleEnum wanted, String accessToken) throws NoAuthorityException {
        RoleEnum received = RoleEnum.valueOf(jwtUtils.getAccessClaims(accessToken).get("role", String.class));

        if (!wanted.equals(received)) {
            throw new NoAuthorityException(wanted, received);
        }
    }

    public String getLoginFromAccessToken(String accessToken) {
        if (!jwtUtils.validateAccessToken(accessToken)) {
            throw new TokenIsInvalidException(accessToken);
        }

        return jwtUtils.getAccessClaims(accessToken).getSubject();
    }
}
