package main.blps_lab2.service.interfaces;

import main.blps_lab2.data.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenInterface {
    Optional<RefreshToken> findByToken(String token);

    int deleteByUserId(Long userId);
}
