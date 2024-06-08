package main.blps_lab3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableKafka
@Slf4j
public class BlpsLab3Application {

    @KafkaListener(topics="msg")
    public void msgListener(String msg){
        log.info(msg);
    }

    public static void main(String[] args) {
        SpringApplication.run(BlpsLab3Application.class, args);
    }

}
