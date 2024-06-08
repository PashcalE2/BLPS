package main.blps_lab3;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.dto.JwtRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableKafka
@Slf4j
public class BlpsLab3Application {

    @KafkaListener(topics = "msg", containerFactory = "consumerFactory")
    public void msgListener(ConsumerRecord<Long, JwtRequest> msg){
        log.info("headers: {} \ntopic: {}\nkey: {} \nvalue: {} \npartition: {} \n", msg.headers(), msg.topic(), msg.key(), msg.value(), msg.partition());
    }

    public static void main(String[] args) {
        SpringApplication.run(BlpsLab3Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }
}
