package main.blps_lab2.repository;


import main.blps_lab2.data.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "select * from \"Course\" where name ~ :name", nativeQuery = true)
    List<Course> getCoursesByName(String name);

    @Query(value = "select * from \"Course\" where id = :courseId", nativeQuery = true)
    Optional<Course> getCourseById(Long courseId);
}
