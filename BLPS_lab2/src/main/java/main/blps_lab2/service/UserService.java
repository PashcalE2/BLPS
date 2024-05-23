package main.blps_lab2.service;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(String email, String password, RoleEnum role) {
        userRepository.registerUser(email, password, role);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
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
