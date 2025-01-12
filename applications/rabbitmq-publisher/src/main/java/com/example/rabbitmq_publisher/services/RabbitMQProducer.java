package com.example.rabbitmq_publisher.services;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.rabbitmq_publisher.constants.RabbitMQConstants.EXCHANGE_NAME;
import static com.example.rabbitmq_publisher.constants.RabbitMQConstants.QUEUE_NAME;

@Service
public class RabbitMQProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void postConstruct() {
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            logger.error("Message: \"{}\" returned: {}", new String(returnedMessage.getMessage().getBody()),
                    returnedMessage.getReplyText());
        });
    }

    public void publishMessage(String statusReportMessage) {
        logger.info("Send message: {}", statusReportMessage);
        Message message = new Message(statusReportMessage.getBytes());
        rabbitTemplate.send(EXCHANGE_NAME, QUEUE_NAME, message);
    }

}
