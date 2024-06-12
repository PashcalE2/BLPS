package main.blps_lab3.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.exception.*;
import main.blps_lab3.kafka.data.BanDetails;
import main.blps_lab3.kafka.data.UnbanDetails;
import main.blps_lab3.model.Course;
import main.blps_lab3.model.RoleEnum;
import main.blps_lab3.model.UserEntity;
import main.blps_lab3.repository.CourseRepository;
import main.blps_lab3.repository.UserRepository;
import main.blps_lab3.repository.XMLUserRepository;
import main.blps_lab3.service.interfaces.AdminServiceInterface;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Profile("publisher")
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService implements AdminServiceInterface {
    private final UserRepository userRepository;
    private final XMLUserRepository xmlUserRepository;
    private final CourseRepository courseRepository;
    private final KafkaProducer<String, BanDetails> kafkaBanDetailsProducer;
    private final KafkaProducer<String, UnbanDetails> kafkaUnbanDetailsProducer;

    @Override
    public void banUser(Long userId) throws UserNotFoundException, UserAlreadyBannedExcpetion, CantBanOrUnbanAdminException {
        UserEntity user = xmlUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("id = %d", userId)));

        if (user.getRole().equals(RoleEnum.ADMIN)) {
            throw new CantBanOrUnbanAdminException(user.getEmail());
        }

        if (user.getBanned()) {
            throw new UserAlreadyBannedExcpetion(user.getEmail());
        }

        xmlUserRepository.banUser(userId);
        userRepository.banUser(userId);

        Date now = Date.from(Instant.now());

        kafkaBanDetailsProducer.send(new ProducerRecord<>(
                "banDetails",
                user.getEmail(),
                new BanDetails(user.getEmail(), now)
        ));
    }

    @Override
    public void unbanUser(Long userId) throws UserNotFoundException, UserAlreadyUnbannedExcpetion, CantBanOrUnbanAdminException {
        UserEntity user = xmlUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("id = %d", userId)));

        if (user.getRole().equals(RoleEnum.ADMIN)) {
            throw new CantBanOrUnbanAdminException(user.getEmail());
        }

        if (!user.getBanned()) {
            throw new UserAlreadyUnbannedExcpetion(user.getEmail());
        }

        xmlUserRepository.unbanUser(userId);
        userRepository.unbanUser(userId);

        Date now = Date.from(Instant.now());

        kafkaUnbanDetailsProducer.send(new ProducerRecord<>(
                "unbanDetails",
                user.getEmail(),
                new UnbanDetails(user.getEmail(), now)
        ));
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
