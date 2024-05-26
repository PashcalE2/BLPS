package main.blps_lab2.service.interfaces;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.exception.ClientAlreadyRegisteredException;
import main.blps_lab2.exception.ClientRegisterException;

import java.util.Optional;

public interface UserServiceInterface {

    void registerUser(String email, String rawPassword, RoleEnum role) throws ClientAlreadyRegisteredException, ClientRegisterException;

    Optional<UserEntity> findByEmail(String email);

    Optional<BankCard> findBankCardByUserId(Long userId);

    void banUser(Long userId);

    void unbanUser(Long userId);
}
