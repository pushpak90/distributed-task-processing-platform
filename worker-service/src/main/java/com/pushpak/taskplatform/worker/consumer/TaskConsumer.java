package com.pushpak.taskplatform.worker.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.pushpak.taskplatform.worker.enums.TaskStatus;
import com.pushpak.taskplatform.worker.model.Task;
import com.pushpak.taskplatform.worker.repository.TaskRepository;
import com.pushpak.taskplatform.worker.service.TaskExecutionLogService;
import com.pushpak.taskplatform.worker.service.TaskProcessor;
import com.pushpak.taskplatform.worker.service.TaskProcessorFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskRepository taskRepository;
    private final TaskProcessorFactory taskProcessorFactory;
    private final TaskExecutionLogService taskExecutionLogService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "task_queue")
    public void processTask(Long taskId) {

        log.info("Received taskId from queue: {}", taskId);

        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            log.warn("Task {} not found in DB. Skipping message.", taskId);
            return;
        }

        int updated = taskRepository.markAsProcessingIfPending(
                taskId,
                TaskStatus.PENDING,
                TaskStatus.PROCESSING);

        if (updated == 0) {
            log.warn("Task {} already processed or picked by another worker. Skipping.", taskId);
            return;
        }

        var logEntry = taskExecutionLogService.startLog(task);

        try {

            log.info("Processing task {}", taskId);

            TaskProcessor processor = taskProcessorFactory.getProcessor(task.getTaskType());

            // processor.process(task);

            throw new RuntimeException("Force failure for testing");
            
            // task.setStatus(TaskStatus.COMPLETED);
            // task.setProcessedAt(LocalDateTime.now());
            // taskRepository.save(task);

            // taskExecutionLogService.success(logEntry);

            // log.info("Task completed {}", taskId);

        } catch (Exception e) {

            log.error("Task processing failed {}", taskId);

            taskExecutionLogService.failure(logEntry, e.getMessage());

            int retryCount = task.getRetryCount() + 1;
            task.setRetryCount(retryCount);

            if (retryCount >= 3) {
                task.setStatus(TaskStatus.FAILED);
                taskRepository.save(task);

                log.error("Task moved to FAILED state {}", taskId);
                throw new RuntimeException("Send to DLQ");
            } else {
                task.setStatus(TaskStatus.PENDING); // or RETRYING if enum exists
                taskRepository.save(task);

                log.warn("Retrying task {} attempt {} of 3", taskId, retryCount);
                rabbitTemplate.convertAndSend("task_retry_queue", taskId);
            }
        }
    }
}