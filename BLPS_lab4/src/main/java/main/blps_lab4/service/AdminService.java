package main.blps_lab4.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.exception.*;
import main.blps_lab4.model.Course;
import main.blps_lab4.model.RoleEnum;
import main.blps_lab4.model.UserEntity;
import main.blps_lab4.repository.CourseRepository;
import main.blps_lab4.repository.UserRepository;
import main.blps_lab4.repository.XMLUserRepository;
import main.blps_lab4.service.interfaces.AdminServiceInterface;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService implements AdminServiceInterface {
    private final UserRepository userRepository;
    private final XMLUserRepository xmlUserRepository;
    private final CourseRepository courseRepository;

    @Override
    public UserEntity banUser(Long userId) throws UserNotFoundException, UserAlreadyBannedException, CantBanOrUnbanAdminException {
        UserEntity user = xmlUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("id = %d", userId)));

        if (user.getRole().equals(RoleEnum.ADMIN)) {
            throw new CantBanOrUnbanAdminException(user.getEmail());
        }

        if (user.getBanned()) {
            throw new UserAlreadyBannedException(user.getEmail());
        }

        xmlUserRepository.banUser(userId);
        userRepository.banUser(userId);

        return user;
    }

    @Override
    public UserEntity unbanUser(Long userId) throws UserNotFoundException, UserAlreadyUnbannedException, CantBanOrUnbanAdminException {
        UserEntity user = xmlUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("id = %d", userId)));

        if (user.getRole().equals(RoleEnum.ADMIN)) {
            throw new CantBanOrUnbanAdminException(user.getEmail());
        }

        if (!user.getBanned()) {
            throw new UserAlreadyUnbannedException(user.getEmail());
        }

        xmlUserRepository.unbanUser(userId);
        userRepository.unbanUser(userId);

        Date now = Date.from(Instant.now());

        return user;

        /*
        kafkaUnbanDetailsProducer.send(new ProducerRecord<>(
                "unbanDetails",
                user.getEmail(),
                new UnbanDetails(user.getEmail(), now)
        ));
        */
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
