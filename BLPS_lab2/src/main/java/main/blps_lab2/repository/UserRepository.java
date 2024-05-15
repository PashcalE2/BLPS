package main.blps_lab2.repository;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.User;
import main.blps_lab2.data.CourseInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "insert into \"User\" values (default, :email, :password, :role)", nativeQuery = true)
    void registerClient(String email, String password, RoleEnum role);

    @Query(value = "select * from \"User\" where email=:email and password=:password", nativeQuery = true)
    Optional<User> findUserByEmailAndPassword(String email, String password);

    @Query(value = "select id, user_id as userId, serial_number as serialNumber, validity_date as validityDate, cvv, money from \"BankCard\" where user_id = :userId", nativeQuery = true)
    Optional<BankCard> findBankCardByUserId(Long userId);

    @Modifying
    @Query(value = "call \"attach_user_card\"(:userId, :serialNumber, :validityDate, :cvv)", nativeQuery = true)
    void attachClientCard(Long userId, String serialNumber, String validityDate, String cvv);

    @Modifying
    @Query(value = "insert into \"UsersCourses\" (user_id, course_id) values (:userId, :courseId)", nativeQuery = true)
    void courseSignUp(Long userId, Long courseId);

    @Query(value = "select id, name, price from \"Course\" where name ~ :name", nativeQuery = true)
    List<CourseInterface> getCoursesByName(String name);

    @Query(value = "select id, name, price from \"Course\" where id = :courseId", nativeQuery = true)
    Optional<CourseInterface> getCourseById(Long courseId);

    @Query(value = "select (select count(*) from \"UsersCourses\" where user_id = :userId and course_id = :courseId) = 1", nativeQuery = true)
    Boolean isUserSignedUpForCourse(Long userId, Long courseId);

    Optional<User> findUserByEmail(String email);
}
