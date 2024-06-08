package main.blps_lab3.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.exception.UserAlreadyRegisteredException;
import main.blps_lab3.exception.UserRegisterException;
import main.blps_lab3.model.RoleEnum;
import main.blps_lab3.model.UserEntity;
import main.blps_lab3.repository.UserRepository;
import main.blps_lab3.repository.XMLUserRepository;
import main.blps_lab3.security.MainPasswordEncoder;
import main.blps_lab3.service.interfaces.UserServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final XMLUserRepository xmlUserRepository;
    private final MainPasswordEncoder passwordEncoder = new MainPasswordEncoder();

    @Override
    public void registerUser(String email, String rawPassword, RoleEnum role) throws UserAlreadyRegisteredException, UserRegisterException {
        if (xmlUserRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyRegisteredException(email);
        }

        String passwordHash = passwordEncoder.encode(rawPassword);

        try {
            userRepository.registerUser(email, passwordHash, role.getAuthority());
            xmlUserRepository.save(userRepository.findByEmail(email).get());
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new UserRegisterException(email);
        }

        log.info(String.format("Зарегистрирован пользователь:\n%s\n%s\n", email, rawPassword));
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return xmlUserRepository.findByEmail(email);
    }
}
