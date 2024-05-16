package main.blps_lab2.service;

import main.blps_lab2.data.Course;
import main.blps_lab2.repository.CourseRepository;
import main.blps_lab2.service.interfaces.CourseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements CourseServiceInterface {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void insertNewCourse() {
        courseRepository.insertNewCourse();
    }

    @Override
    public void updateCourseById(Long course_id, String name, Integer price) {
        courseRepository.updateCourseById(course_id, name, price);
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseRepository.getCoursesByName(name);
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.getCourseById(courseId);
    }
}
