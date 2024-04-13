package main.blps_lab1.repository;

import main.blps_lab1.data.Client;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository {
    @Query(value = "select * from Client where email=:email and password=:password", nativeQuery = true)
    Optional<Client> findClientByEmailAndPassword(String email, String password);

    @Query(value = "update ClientProfile set card_serial=:card_serial, card_validity=:card_validity, card_cvv=:card_cvv where email=:email", nativeQuery = true)
    void updateClientCard(String email, String card_serial, String card_validity, String card_cvv);

    @Modifying
    @Query(value = "insert into ClientsCourses client_id, course_id values (:client_id, :course_id)", nativeQuery = true)
    void courseSignUp(Long client_id, Long course_id);
}
