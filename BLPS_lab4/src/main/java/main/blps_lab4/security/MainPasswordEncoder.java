package main.blps_lab4.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MainPasswordEncoder extends BCryptPasswordEncoder {
}
