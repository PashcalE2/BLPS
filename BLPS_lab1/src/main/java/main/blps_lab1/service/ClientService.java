package main.blps_lab1.service;

import main.blps_lab1.data.ClientInterface;
import main.blps_lab1.data.CourseInterface;
import main.blps_lab1.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements ClientServiceInterface {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void registerClient(String email, String password) {
        clientRepository.registerClient(email, password);
    }

    @Override
    public Optional<ClientInterface> findClientByEmailAndPassword(String email, String password) {
        return clientRepository.findClientByEmailAndPassword(email, password);
    }

    @Override
    public void updateClientCard(String email, String password, String card_serial, String card_validity, String card_cvv) {
        clientRepository.updateClientCard(email, password, card_serial, card_validity, card_cvv);
    }


    @Override
    public void courseSignUp(Long client_id, Long course_id) {
        clientRepository.courseSignUp(client_id, course_id);
    }

    @Override
    public List<CourseInterface> getCoursesByName(String filter) {
        return clientRepository.getCoursesByName(filter);
    }

    @Override
    public Optional<CourseInterface> getCourseById(Long course_id) {
        return clientRepository.getCourseById(course_id);
    }

    @Override
    public Boolean isClientSignedUpForCourse(Long client_id, Long course_id) {
        return clientRepository.isClientSignedUpForCourse(client_id, course_id);
    }
}
