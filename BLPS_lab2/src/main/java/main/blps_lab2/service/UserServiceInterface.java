package main.blps_lab2.service;

import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.User;
import main.blps_lab2.data.CourseInterface;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    void registerUser(String email, String password, RoleEnum role);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    void updateClientCard(Long userId, Long cardId);

    void courseSignUp(Long clientId, Long courseId);

    List<CourseInterface> getCoursesByName(String name);

    Optional<CourseInterface> getCourseById(Long courseId);

    Boolean isUserSignedUpForCourse(Long clientId, Long courseId);
}
