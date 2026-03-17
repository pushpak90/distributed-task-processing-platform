package com.pushpak.taskplatform.worker.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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

    @RabbitListener(queues = "task_queue")
    public void processTask(Long taskId) {

        log.info("Received taskId from queue: {}", taskId);

        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            log.warn("Task {} not found in DB. Skipping message.", taskId);
            return;
        }

        if ("COMPLETED".equals(task.getStatus())) {
            log.info("Task already completed. Skipping {}", taskId);
            return;
        }

        // START EXECUTION LOG
        var logEntry = taskExecutionLogService.startLog(task);

        try {

            log.info("Processing task {}", taskId);

            task.setStatus("PROCESSING");
            taskRepository.save(task);

            TaskProcessor processor = taskProcessorFactory.getProcessor(task.getTaskType());

            processor.process(task);

            task.setStatus("COMPLETED");
            task.setProcessedAt(LocalDateTime.now());
            taskRepository.save(task);

            // SUCCESS LOG
            taskExecutionLogService.success(logEntry);

            log.info("Task completed {}", taskId);

        } catch (Exception e) {

            log.error("Task processing failed {}", taskId);

            // FAILURE LOG
            taskExecutionLogService.failure(logEntry, e.getMessage());

            task.setRetryCount(task.getRetryCount() + 1);

            if (task.getRetryCount() >= 3) {
                task.setStatus("FAILED");
                taskRepository.save(task);
                log.error("Task moved to FAILED state {}", taskId);
                return;
            }

            taskRepository.save(task);

            throw new RuntimeException("Retry task", e);
        }
    }
}
