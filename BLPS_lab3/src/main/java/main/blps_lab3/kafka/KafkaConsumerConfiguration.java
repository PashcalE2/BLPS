package main.blps_lab3.kafka;

import main.blps_lab3.dto.JwtRequest;
import main.blps_lab3.kafka.data.BanDetails;
import main.blps_lab3.kafka.data.UnbanDetails;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Profile("subscriber")
@Configuration
public class KafkaConsumerConfiguration {
    private final String kafkaServer;
    private final String kafkaGroupId;

    public KafkaConsumerConfiguration(
            @Value("${spring.kafka.bootstrap-servers}") String kafkaServer,
            @Value("${spring.kafka.consumer.group-id}") String kafkaGroupId
    ) {
        this.kafkaServer = kafkaServer;
        this.kafkaGroupId = kafkaGroupId;
    }


    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);

        return props;
    }


    public ConsumerFactory<String, BanDetails> banDetailsConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaBanDetailsListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BanDetails> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(banDetailsConsumerFactory());
        return factory;
    }


    public ConsumerFactory<String, UnbanDetails> unbanDetailsConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaUnbanDetailsListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UnbanDetails> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(unbanDetailsConsumerFactory());
        return factory;
    }
}
