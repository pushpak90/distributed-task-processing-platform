package com.pushpak.taskplatform.worker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

@Configuration
public class RabbitMQConfig {
    public static final String TASK_QUEUE = "task_queue";
    public static final String RETRY_QUEUE = "task_retry_queue";
    public static final String DLQ_QUEUE = "task_diq";
    public static final String DLX = "task_dlx";

    @Bean
    public Queue taskQueue() {
        return QueueBuilder.durable(TASK_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RETRY_QUEUE)
                .build();
    }

    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DLX);
    }

    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(RETRY_QUEUE)
                .withArgument("x-message-ttl", 5000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", TASK_QUEUE)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_QUEUE);
    }

}
