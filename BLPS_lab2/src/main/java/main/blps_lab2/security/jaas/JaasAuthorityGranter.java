package main.blps_lab2.security.jaas;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.exception.ClientNotFoundException;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.repository.XMLUserRepository;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class JaasAuthorityGranter implements AuthorityGranter {
    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public Set<String> grant(Principal principal) {
        UserEntity user = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new ClientNotFoundException(principal.getName(), ""));
        return Collections.singleton(user.getRole().getAuthority());
    }
}
