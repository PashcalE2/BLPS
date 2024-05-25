package main.blps_lab2.service.interfaces;

import main.blps_lab2.data.Course;

import java.util.List;
import java.util.Optional;

public interface CourseServiceInterface {
    void save(Course course);

    void updateCourseById(Course course);

    List<Course> getCoursesByName(String name);

    Optional<Course> getCourseById(Long courseId);
}
