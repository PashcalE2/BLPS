package main.blps_lab3.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MailConfiguration {
    private String adminEmail;

    public MailConfiguration(@Value("${admin.email}") String adminEmail) {
        this.adminEmail = adminEmail;
    }
}
