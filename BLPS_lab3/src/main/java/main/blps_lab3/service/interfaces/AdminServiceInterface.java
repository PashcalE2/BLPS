package main.blps_lab3.service.interfaces;

import main.blps_lab3.exception.CourseNotFoundException;
import main.blps_lab3.model.Course;

public interface AdminServiceInterface {
    void banUser(Long userId);

    void unbanUser(Long userId);

    void createNewCourse(Course course);

    void updateCourseById(Course course);

    int loadUsersFromDB();

    void blockCourse(Long courseId) throws CourseNotFoundException;

    void unblockCourse(Long courseId) throws CourseNotFoundException;
}
