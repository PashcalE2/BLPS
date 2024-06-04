package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import main.blps_lab2.model.Course;
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
    public List<Course> getCoursesByName(String name) {
        return courseRepository.getCoursesByName(name);
    }

}
