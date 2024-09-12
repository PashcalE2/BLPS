package main.blps_lab4.service.interfaces;

import main.blps_lab4.exception.*;
import main.blps_lab4.model.Course;
import main.blps_lab4.model.UserEntity;

public interface AdminServiceInterface {
    UserEntity banUser(Long userId) throws UserNotFoundException, UserAlreadyBannedException, CantBanOrUnbanAdminException;

    UserEntity unbanUser(Long userId) throws UserNotFoundException, UserAlreadyUnbannedException, CantBanOrUnbanAdminException;

    void createNewCourse(Course course);

    void updateCourseById(Course course);

    int loadUsersFromDB();

    void blockCourse(Long courseId) throws CourseNotFoundException;

    void unblockCourse(Long courseId) throws CourseNotFoundException;
}
