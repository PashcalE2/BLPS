package main.blps_lab3.service.interfaces;

import main.blps_lab3.model.Course;

import java.util.List;

public interface CourseServiceInterface {
    List<Course> getCoursesByName(String name);
}
