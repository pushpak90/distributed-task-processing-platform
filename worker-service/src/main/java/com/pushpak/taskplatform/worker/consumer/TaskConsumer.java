package com.pushpak.taskplatform.worker.consumer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.pushpak.taskplatform.worker.model.Task;
import com.pushpak.taskplatform.worker.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskConsumer {
    private final TaskRepository taskRepository;

    @RabbitListener(queues = "task_queue")
    public void processTask(Long taskId){
        log.info("Recevied taskId from queue : {}", taskId);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task Not Found"));
        log.info("Processing task {}", task.getId());

        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        task.setStatus("COMPLETED");
        task.setProcessedAt(LocalDateTime.now());

        taskRepository.save(task);

        log.info("Task Completed : {}", task.getId());
    }
}
