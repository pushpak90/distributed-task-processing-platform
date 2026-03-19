package com.pushpak.taskplatform.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.pushpak.taskplatform.api.dto.CreateTaskRequest;
import com.pushpak.taskplatform.api.model.Task;

public interface TaskService {
    Task createtask(CreateTaskRequest request);
    Task getTaskById(Long id);
    List<Task> getAllTask();
    List<Task> findByStatus(String status);
    Page<Task> getTasks(int page, int size);
    Page<Task> getTasks(String status, int page, int size);
}
