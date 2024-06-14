package main.blps_rabbitmq.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_rabbitmq.service.Publisher;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("publisher")
@RestController
@CrossOrigin
@RequestMapping("default")
@Slf4j
@AllArgsConstructor
public class Default {
    private final Publisher publisher;

    @PostMapping("test")
    public ResponseEntity<?> test(@RequestParam(defaultValue = "0") Integer num) throws MqttException {
        log.debug("Controller: test: {}", num);

        MqttMessage mqttMessage = new MqttMessage(num.toString().getBytes());
        publisher.send("test", mqttMessage);

        return new ResponseEntity<>("Sent %d".formatted(num), HttpStatus.OK);
    }
}
