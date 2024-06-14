package main.blps_rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Profile("subscriber")
@Service
@Slf4j
public class Subscriber {
    @JmsListener(destination = "jms.queue")
    public void listener(Integer num) {
        log.info("Received: {}", num);
    }
}
