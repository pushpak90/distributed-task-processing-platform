package com.pushpak.taskplatform.api.service;

import com.pushpak.taskplatform.api.dto.CreateTaskRequest;
import com.pushpak.taskplatform.api.model.Task;

public interface TaskService {
    Task createtask(CreateTaskRequest request);
}
