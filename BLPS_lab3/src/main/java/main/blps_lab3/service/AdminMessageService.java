package main.blps_lab3.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.kafka.data.BanDetails;
import main.blps_lab3.kafka.data.UnbanDetails;
import main.blps_lab3.service.interfaces.MailServiceInterface;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile("subscriber")
@Service
@Slf4j
@AllArgsConstructor
public class AdminMessageService {
    private final MailServiceInterface mailService;

    @KafkaListener(topics = "banDetails", containerFactory = "kafkaBanDetailsListenerContainerFactory")
    public void banDetailsEmail(ConsumerRecord<String, BanDetails> banRecord) {
        log.info("Бан (banDetailsEmail): \n{} \n{}", banRecord.value().getUserEmail(), banRecord.value().getDate());

        mailService.sendEmail(
                banRecord.value().getUserEmail(),
                "Вам бан от " + banRecord.value().getDate(),
                "Поздравляем!"
        );
    }

    @KafkaListener(topics = "unbanDetails", containerFactory = "kafkaUnbanDetailsListenerContainerFactory")
    public void unbanDetailsEmail(ConsumerRecord<String, UnbanDetails> unbanRecord) {
        log.info("Разбан (unbanDetailsEmail): \n{} \n{}", unbanRecord.value().getUserEmail(), unbanRecord.value().getDate());

        mailService.sendEmail(
                unbanRecord.value().getUserEmail(),
                "Вам разбан от " + unbanRecord.value().getDate(),
                "Поздравляем!"
        );
    }
}
