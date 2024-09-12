package main.blps_lab4.repository;


import main.blps_lab4.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "select * from \"Course\" where name ~ :name", nativeQuery = true)
    List<Course> getCoursesByName(String name);

    @Query(value = "select * from \"Course\" where id = :courseId", nativeQuery = true)
    Optional<Course> getCourseById(Long courseId);

    @Transactional
    @Modifying
    @Query(value = "insert into \"Course\" values (default, :name, :price, false)", nativeQuery = true)
    void insert(String name, Integer price);

    @Transactional
    @Modifying
    @Query(value = "update \"Course\" set name = :name, price = :price where id = :courseId", nativeQuery = true)
    void updateCourseById(Long courseId, String name, Integer price);

    @Transactional
    @Modifying
    @Query(value = "update \"Course\" set blocked = true where id = :courseId", nativeQuery = true)
    void blockCourse(Long courseId);

    @Transactional
    @Modifying
    @Query(value = "update \"Course\" set blocked = false where id = :courseId", nativeQuery = true)
    void unblockCourse(Long courseId);
}
