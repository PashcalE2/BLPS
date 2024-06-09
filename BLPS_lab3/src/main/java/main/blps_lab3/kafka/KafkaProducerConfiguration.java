package main.blps_lab3.kafka;

import main.blps_lab3.kafka.data.BanDetails;
import main.blps_lab3.kafka.data.UnbanDetails;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Profile("publisher")
@Configuration
public class KafkaProducerConfiguration {
    private final String kafkaServer;

    public KafkaProducerConfiguration(
            @Value("${spring.kafka.bootstrap-servers}") String kafkaServer
    ) {
        this.kafkaServer = kafkaServer;
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean
    public KafkaProducer<String, BanDetails> kafkaBanDetailsProducer() {
        return new KafkaProducer<>(producerConfigs());
    }

    @Bean
    public KafkaProducer<String, UnbanDetails> kafkaUnbanDetailsProducer() {
        return new KafkaProducer<>(producerConfigs());
    }
}
