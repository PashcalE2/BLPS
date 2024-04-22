package main.blps_lab1.service;

import main.blps_lab1.data.ClientInterface;
import main.blps_lab1.data.CourseInterface;

import java.util.List;
import java.util.Optional;

public interface ClientServiceInterface {

    void registerClient(String email, String password);

    Optional<ClientInterface> findClientByEmailAndPassword(String email, String password);

    void updateClientCard(String email, String password, String card_serial, String card_validity, String card_cvv);

    void courseSignUp(Long client_id, Long course_id);

    List<CourseInterface> getCoursesByFilter(String filter);

    Optional<CourseInterface> getCourseById(Long course_id);

    Boolean isClientSignedUpForCourse(Long client_id, Long course_id);
}
