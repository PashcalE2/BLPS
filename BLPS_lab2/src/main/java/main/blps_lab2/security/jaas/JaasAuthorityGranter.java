package main.blps_lab2.security.jaas;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import main.blps_lab2.model.UserEntity;
import main.blps_lab2.exception.UserNotFoundException;
import main.blps_lab2.repository.XMLUserRepository;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class JaasAuthorityGranter implements AuthorityGranter {
    private final XMLUserRepository xmlUserRepository;

    @Override
    @SneakyThrows
    public Set<String> grant(Principal principal) {
        UserEntity user = xmlUserRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        return Collections.singleton(user.getRole().getAuthority());
    }
}
