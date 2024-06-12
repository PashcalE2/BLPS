package main.blps_lab3.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.exception.TokenIsExpiredException;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Profile("publisher")
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends GenericFilterBean {
    private final JwtUtils jwtUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("JWT FILTER: parse Jwt");

        final String token = parseJwt((HttpServletRequest) servletRequest);

        try {
            if (token != null && jwtUtils.validateAccessToken(token)) {
                log.info("JWT FILTER: token is valid");

                final Claims claims = jwtUtils.getAccessClaims(token);
                final JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);

                log.info(String.format("JWT FILTER: Authorities: %s", jwtInfoToken.getAuthorities().stream().findAny().get()));

                jwtInfoToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
            }
        }
        catch (TokenIsExpiredException ignored) {}

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        log.warn("Неправильный заголовок Authorization (нужно: Authorization | Bearer <token>)");

        return null;
    }
}
