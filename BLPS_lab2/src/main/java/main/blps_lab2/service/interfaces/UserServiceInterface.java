package main.blps_lab2.service.interfaces;

import main.blps_lab2.exception.UserAlreadyRegisteredException;
import main.blps_lab2.exception.UserRegisterException;
import main.blps_lab2.model.RoleEnum;
import main.blps_lab2.model.UserEntity;

import java.util.Optional;

public interface UserServiceInterface {

    void registerUser(String email, String rawPassword, RoleEnum role) throws UserAlreadyRegisteredException, UserRegisterException;

    Optional<UserEntity> findByEmail(String email);
}
