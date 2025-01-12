package com.example.rabbitmq_consumer.constants;

public class RabbitMQConstant {

    public static final String UPSTREAM_STATUS_REPORT_QUEUE_NAME = "status-report-queue-upstream";
    public static final String UPSTREAM_DEAD_LETTER_QUEUE_NAME = "dead-letter-queue-upstream";

    public static final String UPSTREAM_STATUS_REPORT_EXCHANGE_NAME = "status-report-exchange-upstream";
    public static final String UPSTREAM_DEAD_LETTER_EXCHANGE_NAME = "dead-letter-exchange-upstream";

    public static final String QUEUE_TYPE_KEY = "x-queue-type";
    public static final String QUORUM_QUEUE_GROUP_SIZE_KEY = "x-quorum-initial-group-size";
    public static final String DEAD_LETTER_EXCHANGE_KEY = "x-dead-letter-exchange";
    public static final String DEAD_LETTER_STRATEGY_KEY = "x-dead-letter-strategy";
    public static final String OVERFLOW_KEY = "x-overflow";
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    public static final String QUORUM_QUEUE_TYPE_VALUE = "quorum";
    public static final int QUORUM_QUEUE_GROUP_SIZE_VALUE = 3;
    public static final String AT_LEAST_ONCE_DLX_STRATEGY_VALUE = "at-least-once";
    public static final String REJECT_PUBLISH_OVERFLOW_VALUE = "reject-publish";
}
