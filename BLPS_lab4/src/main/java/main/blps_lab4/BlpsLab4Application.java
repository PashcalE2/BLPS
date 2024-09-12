package main.blps_lab4;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class BlpsLab4Application {

    public static void main(String[] args) {
        SpringApplication.run(BlpsLab4Application.class, args);
    }

}
