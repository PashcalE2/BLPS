package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.exception.ClientAlreadyRegisteredException;
import main.blps_lab2.exception.ClientAlreadySignedUpException;
import main.blps_lab2.exception.ClientRegisterException;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.repository.XMLUserRepository;
import main.blps_lab2.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final XMLUserRepository xmlUserRepository;

    @Override
    public void registerUser(String email, String password, RoleEnum role) throws ClientAlreadyRegisteredException, ClientRegisterException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ClientAlreadyRegisteredException(email);
        }

        try {
            userRepository.registerUser(email, password, role.getAuthority());
            userRepository.flush();
            xmlUserRepository.save(userRepository.findByEmail(email).get());
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ClientRegisterException(email);
        }

        log.info(String.format("Зарегистрирован пользователь:\n%s\n%s\n", email, password));
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return xmlUserRepository.findByEmail(email);
    }

    @Override
    public Optional<BankCard> findBankCardByUserId(Long userId) {
        return userRepository.findBankCardByUserId(userId);
    }

    @Override
    public void banUser(Long userId) {

    }

    @Override
    public void unbanUser(Long userId) {

    }
}
