package main.blps_lab3.service.interfaces;

import main.blps_lab3.exception.*;
import main.blps_lab3.model.Course;

public interface AdminServiceInterface {
    void banUser(Long userId) throws UserNotFoundException, UserAlreadyBannedExcpetion, CantBanOrUnbanAdminException;

    void unbanUser(Long userId) throws UserNotFoundException, UserAlreadyUnbannedExcpetion, CantBanOrUnbanAdminException;

    void createNewCourse(Course course);

    void updateCourseById(Course course);

    int loadUsersFromDB();

    void blockCourse(Long courseId) throws CourseNotFoundException;

    void unblockCourse(Long courseId) throws CourseNotFoundException;
}
