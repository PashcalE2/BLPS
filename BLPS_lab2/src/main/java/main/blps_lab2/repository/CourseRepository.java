package main.blps_lab2.repository;


import main.blps_lab2.data.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    void insertNewCourse();

    @Modifying
    void updateCourseById(Long course_id, String name, Integer price);

    @Query(value = "select * from \"Course\" where name ~ :name", nativeQuery = true)
    List<Course> getCoursesByName(String name);

    @Query(value = "select * from \"Course\" where id = :courseId", nativeQuery = true)
    Optional<Course> getCourseById(Long courseId);
}
