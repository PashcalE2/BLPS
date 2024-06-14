package main.blps_rabbitmq.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import main.blps_rabbitmq.mqtt.Mqtt;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("subscriber")
@Service
@Slf4j
public class Subscriber {
    @PostConstruct
    public void setupMqtt() throws MqttException {
        log.debug("Subscription to topic");

        Mqtt.subscribe("test", 0, (s, mqttMessage) -> {
            log.info("Received: {}", new String(mqttMessage.getPayload()));
        });
    }
}
