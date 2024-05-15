package main.blps_lab2.service;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.User;
import main.blps_lab2.data.CourseInterface;
import main.blps_lab2.repository.UserRepository;
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
        userRepository.registerClient(email, password, role);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @Override
    public Optional<BankCard> findBankCardByUserId(Long userId) {
        return userRepository.findBankCardByUserId(userId);
    }

    @Override
    public void attachClientCard(Long userId, Long cardId) {
        userRepository.updateClientCard(userId, cardId);
    }

    @Override
    public void courseSignUp(Long clientId, Long courseId) {
        userRepository.courseSignUp(clientId, courseId);
    }

    @Override
    public List<CourseInterface> getCoursesByName(String name) {
        return userRepository.getCoursesByName(name);
    }

    @Override
    public Optional<CourseInterface> getCourseById(Long courseId) {
        return userRepository.getCourseById(courseId);
    }

    @Override
    public Boolean isUserSignedUpForCourse(Long clientId, Long courseId) {
        return userRepository.isUserSignedUpForCourse(clientId, courseId);
    }
}
