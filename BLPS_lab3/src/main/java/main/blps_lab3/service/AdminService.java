package main.blps_lab3.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import main.blps_lab3.exception.CourseNotFoundException;
import main.blps_lab3.model.Course;
import main.blps_lab3.model.UserEntity;
import main.blps_lab3.repository.CourseRepository;
import main.blps_lab3.repository.UserRepository;
import main.blps_lab3.repository.XMLUserRepository;
import main.blps_lab3.service.interfaces.AdminServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        courseRepository.insert(course.getName(), course.getPrice());
    }

    @Override
    @SneakyThrows
    public void updateCourseById(Course course) {
        Optional<Course> db_course = courseRepository.getCourseById(course.getId());

        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(course.getId());
        }

        courseRepository.updateCourseById(course.getId(), course.getName(), course.getPrice());
    }

    @Override
    public int loadUsersFromDB() {
        List<UserEntity> users = userRepository.getAll();
        xmlUserRepository.saveAll(users);
        return users.size();
    }

    @Override
    public void blockCourse(Long courseId) throws CourseNotFoundException {
        Optional<Course> db_course = courseRepository.getCourseById(courseId);

        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        courseRepository.blockCourse(courseId);
    }

    @Override
    public void unblockCourse(Long courseId) throws CourseNotFoundException {
        Optional<Course> db_course = courseRepository.getCourseById(courseId);

        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        courseRepository.unblockCourse(courseId);
    }
}
