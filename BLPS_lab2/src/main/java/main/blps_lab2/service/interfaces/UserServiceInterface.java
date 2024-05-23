package main.blps_lab2.service.interfaces;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.UserEntity;

import java.util.Optional;

public interface UserServiceInterface {

    void registerUser(String email, String password, RoleEnum role);

    Optional<UserEntity> findUserByEmail(String email);

    Optional<BankCard> findBankCardByUserId(Long userId);

    void banUser(Long userId);

    void unbanUser(Long userId);
}
