package main.blps_rabbitmq.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;


public class RabbitMQConfiguration {
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate();
    }
}
