package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import main.blps_lab2.exception.CourseNotFoundException;
import main.blps_lab2.model.Course;
import main.blps_lab2.model.UserEntity;
import main.blps_lab2.repository.CourseRepository;
import main.blps_lab2.repository.UserRepository;
import main.blps_lab2.repository.XMLUserRepository;
import main.blps_lab2.service.interfaces.AdminServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService implements AdminServiceInterface {
    private final UserRepository userRepository;
    private final XMLUserRepository xmlUserRepository;
    private final CourseRepository courseRepository;

    @Override
    public void banUser(Long userId) {
        userRepository.banUser(userId);
        xmlUserRepository.banUser(userId);
    }

    @Override
    public void unbanUser(Long userId) {
        userRepository.unbanUser(userId);
        xmlUserRepository.unbanUser(userId);
    }

    @Override
    public void createNewCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    @SneakyThrows
    public void updateCourseById(Course course) {
        Course db_course = courseRepository
                .findById(course.getId())
                .orElseThrow(() -> new CourseNotFoundException(course.getId()));

        db_course.setName(course.getName());
        db_course.setPrice(course.getPrice());

        courseRepository.save(course);
    }

    @Override
    public int loadUsersFromDB() {
        List<UserEntity> users = userRepository.getAll();
        xmlUserRepository.saveAll(users);
        return users.size();
    }
}
