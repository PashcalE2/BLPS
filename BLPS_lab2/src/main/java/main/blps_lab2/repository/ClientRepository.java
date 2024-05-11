package main.blps_lab2.repository;

import main.blps_lab2.data.Client;
import main.blps_lab2.data.ClientInterface;
import main.blps_lab2.data.CourseInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into Client values (default, :email, :password, null, null, null)", nativeQuery = true)
    void registerClient(String email, String password);

    @Query(value = "select id, email, password, card_serial as cardSerial, card_validity as cardValidity, card_cvv as cardCvv from Client where email=:email and password=:password", nativeQuery = true)
    Optional<ClientInterface> findClientByEmailAndPassword(String email, String password);

    @Modifying
    @Transactional
    @Query(value = "update Client set card_serial=:card_serial, card_validity=:card_validity, card_cvv=:card_cvv where email = :email and password = :password", nativeQuery = true)
    void updateClientCard(String email, String password, String card_serial, String card_validity, String card_cvv);

    @Modifying
    @Transactional
    @Query(value = "insert into ClientsCourses (client_id, course_id) values (:client_id, :course_id)", nativeQuery = true)
    void courseSignUp(Long client_id, Long course_id);

    @Query(value = "select id, name, price from Course where name ~ :filter", nativeQuery = true)
    List<CourseInterface> getCoursesByName(String filter);

    @Query(value = "select id, name, price from Course where id = :course_id", nativeQuery = true)
    Optional<CourseInterface> getCourseById(Long course_id);

    @Query(value = "select (select count(*) from ClientsCourses where client_id = :client_id and course_id = :course_id) = 1", nativeQuery = true)
    Boolean isClientSignedUpForCourse(Long client_id, Long course_id);
}
