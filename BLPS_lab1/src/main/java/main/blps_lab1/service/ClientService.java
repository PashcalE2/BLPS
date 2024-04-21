package main.blps_lab1.service;

import main.blps_lab1.data.ClientInterface;
import main.blps_lab1.data.CourseInterface;
import main.blps_lab1.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements ClientRepository {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void registerClient(String email, String password) {
        clientRepository.registerClient(email, password);
    }

    @Override
    public Optional<ClientInterface> findClientByEmailAndPassword(String email, String password) {
        return clientRepository.findClientByEmailAndPassword(email, password);
    }

    @Override
    public void updateClientCard(String email, String card_serial, String card_validity, String card_cvv) {
        clientRepository.updateClientCard(email, card_serial, card_validity, card_cvv);
    }


    @Override
    public void courseSignUp(Long client_id, Long course_id) {
        clientRepository.courseSignUp(client_id, course_id);
    }

    @Override
    public List<CourseInterface> getCoursesByFilter(String filter) {
        return clientRepository.getCoursesByFilter(filter);
    }
}
