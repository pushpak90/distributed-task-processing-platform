package com.pushpak.taskplatform.api.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.pushpak.taskplatform.api.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendTask(Long taskId){
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_QUEUE, taskId);
    }
}
