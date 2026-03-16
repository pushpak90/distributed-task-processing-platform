package com.pushpak.taskplatform.worker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {
    public static final String TASK_QUEUE = "task_queue";
    
    @Bean
    public Queue taskQueue(){
        return new Queue(TASK_QUEUE, true);
    }
}
