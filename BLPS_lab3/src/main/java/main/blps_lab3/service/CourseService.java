package main.blps_lab3.service;

import lombok.RequiredArgsConstructor;
import main.blps_lab3.model.Course;
import main.blps_lab3.repository.CourseRepository;
import main.blps_lab3.service.interfaces.CourseServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements CourseServiceInterface {
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseRepository.getCoursesByName(name);
    }

}
