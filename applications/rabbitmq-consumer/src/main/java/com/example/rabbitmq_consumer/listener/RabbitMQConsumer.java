package com.example.rabbitmq_consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.example.rabbitmq_consumer.constants.RabbitMQConstant.UPSTREAM_STATUS_REPORT_QUEUE_NAME;

@Component
public class RabbitMQConsumer {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = UPSTREAM_STATUS_REPORT_QUEUE_NAME)
    public void onMessage(Message message) {
        String messageBody = new String(message.getBody());
        logger.info("Received message: {}", messageBody);

        if (messageProcessingFailed()) {
            logger.error("Processing of message: {} failed", messageBody);
        } else {
            logger.info("Successfully processed message: {}", messageBody);
        }
    }

    private boolean messageProcessingFailed() {
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        return randomNumber < 25;
    }
}
