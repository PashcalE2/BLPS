package main.blps_lab2.service;

import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.User;
import main.blps_lab2.data.CourseInterface;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @Override
    public void banUser(Long userId) {

    }

    @Override
    public void unbanUser(Long userId) {

    }
}
