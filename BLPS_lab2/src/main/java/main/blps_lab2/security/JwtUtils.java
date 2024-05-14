package main.blps_lab2.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${secret.key}")
    private String jwtKey;

    @Value("${token.expiration_ms}")
    private long jwtExpirationMs;

    private final SecretKey jwtSecretKey = new SecretKeySpec(jwtKey.getBytes(), "HS256");

    // private final Base64.Decoder decoder = Base64.getUrlDecoder();

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(jwtSecretKey)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        /*
        String[] chunks = token.split("\\.");
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        */

        return new String(Jwts.parser()
                .decryptWith(jwtSecretKey)
                .requireSubject("username")
                .build()
                .parseSignedContent(token)
                .getPayload());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(jwtSecretKey).build().parse(authToken);
            log.info("Valid JWT token: {}", authToken);
            return true;
        }
        catch (MalformedJwtException e) {
            log.error("Invalid JWT token structure: {}", e.getMessage());
        }
        catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        }
        catch (SignatureException e) {
            log.error("JWT token wrong signature: {}", e.getMessage());
        }
        catch (SecurityException e) {
            log.error("JWT security exception: {}", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            log.error("JWT illegal argument: {}", e.getMessage());
        }

        log.warn("Invalid JWT token: {}", authToken);
        return false;
    }
}
