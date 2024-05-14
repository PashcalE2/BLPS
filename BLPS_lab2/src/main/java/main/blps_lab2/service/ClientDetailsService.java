package main.blps_lab2.service;

import main.blps_lab2.data.Client;
import main.blps_lab2.repository.ClientRepository;
import main.blps_lab2.security.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Нет клиентов с такой почтой: " + username));

        return AuthUserDetails.build(client);
    }
}
