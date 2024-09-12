package main.blps_lab4.service.interfaces;

import main.blps_lab4.exception.UserAlreadyRegisteredException;
import main.blps_lab4.exception.UserRegisterException;
import main.blps_lab4.model.RoleEnum;
import main.blps_lab4.model.UserEntity;

import java.util.Optional;

public interface UserServiceInterface {

    void registerUser(String email, String rawPassword, RoleEnum role) throws UserAlreadyRegisteredException, UserRegisterException;

    Optional<UserEntity> findByEmail(String email);
}
