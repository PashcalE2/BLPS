package main.blps_rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import main.blps_rabbitmq.mqtt.Mqtt;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("publisher")
@Service
@Slf4j
public class Publisher {
    public void send(String topic, MqttMessage mqttMessage) throws MqttException {
        Mqtt.publish(topic, mqttMessage);
    }
}
