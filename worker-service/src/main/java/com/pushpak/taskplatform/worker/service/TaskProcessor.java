package com.pushpak.taskplatform.worker.service;

import com.pushpak.taskplatform.worker.model.Task;

public interface TaskProcessor {
    void process(Task task);
}
