package main.blps_lab4.service.interfaces;

import main.blps_lab4.model.Course;

import java.util.List;

public interface CourseServiceInterface {
    List<Course> getCoursesByName(String name);
}
