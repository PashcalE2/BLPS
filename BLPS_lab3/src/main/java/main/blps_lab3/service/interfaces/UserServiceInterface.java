package main.blps_lab3.service.interfaces;

import main.blps_lab3.exception.UserAlreadyRegisteredException;
import main.blps_lab3.exception.UserRegisterException;
import main.blps_lab3.model.RoleEnum;
import main.blps_lab3.model.UserEntity;

import java.util.Optional;

public interface UserServiceInterface {

    void registerUser(String email, String rawPassword, RoleEnum role) throws UserAlreadyRegisteredException, UserRegisterException;

    Optional<UserEntity> findByEmail(String email);
}
