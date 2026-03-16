package com.pushpak.taskplatform.api.service.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pushpak.taskplatform.api.dto.CreateTaskRequest;
import com.pushpak.taskplatform.api.enums.TaskStatus;
import com.pushpak.taskplatform.api.messaging.TaskProducer;
import com.pushpak.taskplatform.api.model.Task;
import com.pushpak.taskplatform.api.repository.TaskRepository;
import com.pushpak.taskplatform.api.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final TaskProducer taskProducer;

    public TaskServiceImpl(TaskRepository taskRepository, TaskProducer taskProducer){
        this.taskRepository = taskRepository;
        this.taskProducer = taskProducer;
    }

    @Override
    public Task createtask(CreateTaskRequest request) {
        Task task = Task.builder()
                    .taskType(request.getTaskType())
                    .payload(request.getPayload())
                    .status(TaskStatus.PENDING.name())
                    .retryCount(0)
                    .createAt(LocalDateTime.now())
                    .build();
        Task savedTask = taskRepository.save(task);
        taskProducer.sendTask(savedTask.getId());
        return savedTask;
    }
    
}
