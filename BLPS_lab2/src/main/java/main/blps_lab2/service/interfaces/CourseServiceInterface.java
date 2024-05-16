package main.blps_lab2.service.interfaces;

import main.blps_lab2.data.Course;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface CourseServiceInterface {
    void insertNewCourse();

    void updateCourseById(Long course_id, String name, Integer price);

    List<Course> getCoursesByName(String name);

    Optional<Course> getCourseById(Long courseId);
}
