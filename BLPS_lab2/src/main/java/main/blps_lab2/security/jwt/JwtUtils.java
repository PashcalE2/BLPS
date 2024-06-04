package main.blps_lab2.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.exception.TokenIsExpiredException;
import main.blps_lab2.model.RoleEnum;
import main.blps_lab2.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final Long jwtAccessExpirationMinutes;
    private final Long jwtRefreshExpirationMinutes;

    public JwtUtils(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
            @Value("${jwt.expiration_minutes.access}") Long jwtAccessExpirationMinutes,
            @Value("${jwt.expiration_minutes.refresh}") Long jwtRefreshExpirationMinutes
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.jwtAccessExpirationMinutes = jwtAccessExpirationMinutes;
        this.jwtRefreshExpirationMinutes = jwtRefreshExpirationMinutes;
    }

    public String generateAccessToken(UserEntity userEntity) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(jwtAccessExpirationMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(userEntity.getEmail())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("role", userEntity.getRole())
                .compact();
    }

    public String generateRefreshToken(UserEntity userEntity) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusMinutes(jwtRefreshExpirationMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(userEntity.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) throws TokenIsExpiredException {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(String refreshToken) throws TokenIsExpiredException {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(String token, Key secret) throws TokenIsExpiredException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException expEx) {
            throw new TokenIsExpiredException(token);
        }
        catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        }
        catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        }
        catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        }
        catch (Exception e) {
            log.error("invalid token", e);
        }

        return false;
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setAuthorities(new HashSet<RoleEnum>() {{
            add(getRole(claims));
        }});

        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }

    private static RoleEnum getRole(Claims claims) {
        return RoleEnum.valueOf(claims.get("role", String.class));
    }
}
