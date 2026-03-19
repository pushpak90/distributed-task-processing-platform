package com.pushpak.taskplatform.api.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pushpak.taskplatform.api.dto.CreateTaskRequest;
import com.pushpak.taskplatform.api.enums.TaskStatus;
// import com.pushpak.taskplatform.api.messaging.TaskProducer;
import com.pushpak.taskplatform.api.model.Task;
import com.pushpak.taskplatform.api.model.TaskOutbox;
import com.pushpak.taskplatform.api.repository.TaskOutboxRepository;
import com.pushpak.taskplatform.api.repository.TaskRepository;
import com.pushpak.taskplatform.api.service.TaskService;
import org.springframework.data.domain.Pageable;

import jakarta.transaction.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    // private final TaskProducer taskProducer;
    private final TaskOutboxRepository taskOutboxRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
            // TaskProducer taskProducer,
            TaskOutboxRepository taskOutboxRepository) {
        this.taskRepository = taskRepository;
        // this.taskProducer = taskProducer;
        this.taskOutboxRepository = taskOutboxRepository;
    }

    @Transactional
    @Override
    public Task createtask(CreateTaskRequest request) {
        Task task = Task.builder()
                .taskType(request.getTaskType())
                .payload(request.getPayload())
                .status(TaskStatus.PENDING)
                .retryCount(0)
                .createAt(LocalDateTime.now())
                .build();
        Task savedTask = taskRepository.save(task);
        // taskProducer.sendTask(savedTask.getId());
        TaskOutbox outbox = TaskOutbox.builder()
                .taskId(savedTask.getId())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        taskOutboxRepository.save(outbox);
        return savedTask;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task Not Found By ID : " + id));
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByStatus(String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskRepository.findByStatus(taskStatus);
    }

    @Override
    public Page<Task> getTasks(int page, int size) {
        return taskRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Task> getTasks(String status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (status != null) {
            return taskRepository.findByStatus(
                    TaskStatus.valueOf(status.toUpperCase()),
                    pageable);
        }

        return taskRepository.findAll(pageable);
    }
}
