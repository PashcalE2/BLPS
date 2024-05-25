package main.blps_lab2.security;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.exception.ClientNotFoundXMLException;
import main.blps_lab2.repository.XMLUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.spi.LoginModule;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JaasLoginModule implements LoginModule {
    private PasswordEncoder passwordEncoder;
    private String login;
    private boolean loginSucceeded = false;
    private Subject subject;
    private CallbackHandler callbackHandler;
    private XMLUserRepository userRepository;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.callbackHandler = callbackHandler;
        this.subject = subject;
        this.userRepository = (XMLUserRepository) options.get("userRepository");
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @SneakyThrows
    public boolean login()  {
        log.info("Попытка входа");

        NameCallback nameCallback = new NameCallback("login: ");
        PasswordCallback passwordCallback = new PasswordCallback("password: ", false);

        callbackHandler.handle(new Callback[] { nameCallback, passwordCallback });
        login = nameCallback.getName();
        String password = new String(passwordCallback.getPassword());
        Optional<UserEntity> user = userRepository.findByEmail(login);

        if (user.isPresent()) {
            loginSucceeded = passwordEncoder.matches(password, user.get().getPassword());
        } else {
            return false;
        }

        return loginSucceeded;
    }

    @Override
    @SneakyThrows
    public boolean commit() {
        if (!loginSucceeded) return false;
        if (login == null) throw new ClientNotFoundXMLException(null);

        log.info("Пользователь (%s) прошел аутентификацию");
        Principal principal = (UserPrincipal) () -> login;
        subject.getPrincipals().add(principal);
        return true;
    }

    @Override
    public boolean abort() {
        log.warn(String.format("Пользователь (%s) провалил аутентификацию", login));

        return false;
    }

    @Override
    public boolean logout() {
        log.info(String.format("Пользователь (%s) вышел из системы", login));

        subject.getPrincipals().removeIf(principal -> principal instanceof UserPrincipal);
        loginSucceeded = false;
        return true;
    }
}
