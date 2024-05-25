package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import main.blps_lab2.data.Course;
import main.blps_lab2.exception.CourseNotFoundException;
import main.blps_lab2.repository.CourseRepository;
import main.blps_lab2.service.interfaces.CourseServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService implements CourseServiceInterface {
    private final CourseRepository courseRepository;

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    @Transactional
    @SneakyThrows
    public void updateCourseById(Course course) {
        Course db_course = courseRepository
                .findById(course.getId())
                .orElseThrow(() -> new CourseNotFoundException(course.getId()));

        db_course.setName(course.getName());
        db_course.setPrice(course.getPrice());

        courseRepository.save(db_course);
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
