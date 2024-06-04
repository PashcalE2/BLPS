package main.blps_lab2.service.interfaces;

import main.blps_lab2.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseServiceInterface {
    List<Course> getCoursesByName(String name);
}
