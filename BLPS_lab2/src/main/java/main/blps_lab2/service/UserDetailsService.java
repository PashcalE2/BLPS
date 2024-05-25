package main.blps_lab2.service;

import lombok.RequiredArgsConstructor;
import main.blps_lab2.data.UserEntity;
import main.blps_lab2.repository.XMLUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final XMLUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Нет клиентов с такой почтой: " + username));

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }
}
