package com.example.rabbitmq_consumer.configs;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.rabbitmq_consumer.constants.RabbitMQConstant.*;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(UPSTREAM_STATUS_REPORT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue quorumQueue() {
        return QueueBuilder.durable(UPSTREAM_STATUS_REPORT_QUEUE_NAME)
                .withArgument(QUEUE_TYPE_KEY, QUORUM_QUEUE_TYPE_VALUE)
                .withArgument(QUORUM_QUEUE_GROUP_SIZE_KEY, QUORUM_QUEUE_GROUP_SIZE_VALUE)
                .withArgument(DEAD_LETTER_EXCHANGE_KEY, UPSTREAM_DEAD_LETTER_EXCHANGE_NAME)
                .withArgument(DEAD_LETTER_STRATEGY_KEY, AT_LEAST_ONCE_DLX_STRATEGY_VALUE)
                .withArgument(OVERFLOW_KEY, REJECT_PUBLISH_OVERFLOW_VALUE)
                .withArgument(DEAD_LETTER_ROUTING_KEY, UPSTREAM_DEAD_LETTER_QUEUE_NAME)
                .build();
    }

    @Bean
    public Binding quorumBinding(Queue quorumQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(quorumQueue).to(directExchange).with(UPSTREAM_STATUS_REPORT_QUEUE_NAME);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(UPSTREAM_DEAD_LETTER_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue deadLetterQuorumQueue() {
        return QueueBuilder.durable(UPSTREAM_DEAD_LETTER_QUEUE_NAME)
                .withArgument(QUEUE_TYPE_KEY, QUORUM_QUEUE_TYPE_VALUE)
                .withArgument(QUORUM_QUEUE_GROUP_SIZE_KEY, QUORUM_QUEUE_GROUP_SIZE_VALUE)
                .build();
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQuorumQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQuorumQueue).to(deadLetterExchange).with(UPSTREAM_DEAD_LETTER_QUEUE_NAME);
    }

}

