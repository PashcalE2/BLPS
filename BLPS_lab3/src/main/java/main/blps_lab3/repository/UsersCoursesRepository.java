package main.blps_lab3.repository;

import main.blps_lab3.model.UsersCourses;
import main.blps_lab3.model.UsersCoursesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UsersCoursesRepository extends JpaRepository<UsersCourses, UsersCoursesPK> {
    @Query(value = "select (select count(*) from \"UsersCourses\" where user_id = :clientId and course_id = :courseId) = 1", nativeQuery = true)
    Boolean isClientSignedUpForCourse(Long clientId, Long courseId);

    @Modifying
    @Query(value = "insert into \"UsersCourses\" (user_id, course_id) values (:userId, :courseId)", nativeQuery = true)
    void courseSignUp(Long userId, Long courseId);
}
