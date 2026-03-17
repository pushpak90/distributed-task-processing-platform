package com.pushpak.taskplatform.worker.service;

import com.pushpak.taskplatform.worker.model.Task;
import com.pushpak.taskplatform.worker.model.TaskExecutionLog;

public interface TaskExecutionLogService {
    TaskExecutionLog startLog(Task task);
    void success(TaskExecutionLog log);
    void failure(TaskExecutionLog log, String error);
}

