package main.blps_lab3.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/msg")
@Slf4j
public class MsgController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(value = "/sendOrder")
    public ResponseEntity<?> sendOrder(
            @RequestParam String msgId,
            @RequestBody String msg
    ) {
        kafkaTemplate.send("msg", msgId, msg);

        return new ResponseEntity<>("Sent", HttpStatus.OK);
    }

    @PostMapping(value = "/sendMsg")
    public ResponseEntity<?> sendMsg(
            @RequestParam String msgId,
            @RequestBody String msg
    ) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("msg", msgId, msg);

        future.whenComplete((result, throwable) -> {
            log.info(result.toString());
        });

        kafkaTemplate.flush();

        return new ResponseEntity<>("Sent", HttpStatus.OK);
    }
}
