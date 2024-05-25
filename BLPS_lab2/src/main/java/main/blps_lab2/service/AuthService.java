package main.blps_lab2.service;


import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.security.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtUtils jwtUtils;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        UserEntity userEntity = userService.findByEmail(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        if (userEntity.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtUtils.generateAccessToken(userEntity);
            final String refreshToken = jwtUtils.generateRefreshToken(userEntity);
            refreshStorage.put(userEntity.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getNewAccessToken(@NonNull RefreshJwtRequest refreshJwtRequest) throws AuthException {
        String refreshToken = refreshJwtRequest.getRefreshToken();

        if (jwtUtils.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtUtils.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity userEntity = userService.findByEmail(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtUtils.generateAccessToken(userEntity);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse getNewRefreshToken(@NonNull RefreshJwtRequest refreshJwtRequest) throws AuthException {
        String refreshToken = refreshJwtRequest.getRefreshToken();

        if (jwtUtils.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtUtils.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity userEntity = userService.findByEmail(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtUtils.generateAccessToken(userEntity);
                final String newRefreshToken = jwtUtils.generateRefreshToken(userEntity);
                refreshStorage.put(userEntity.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuth getAuthInfo() {
        return (JwtAuth) SecurityContextHolder.getContext().getAuthentication();
    }

}
