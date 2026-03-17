package com.pushpak.taskplatform.worker.service.ServiceImpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pushpak.taskplatform.worker.model.Task;
import com.pushpak.taskplatform.worker.model.TaskExecutionLog;
import com.pushpak.taskplatform.worker.repository.TaskExecutionLogRespository;
import com.pushpak.taskplatform.worker.service.TaskExecutionLogService;

@Service
public class TaskExecutionLogServiceImpl implements TaskExecutionLogService{

    private final TaskExecutionLogRespository logRepository;

    public TaskExecutionLogServiceImpl(TaskExecutionLogRespository logRepository){
        this.logRepository = logRepository;
    }

    @Override
    public TaskExecutionLog startLog(Task task) {
        TaskExecutionLog log = TaskExecutionLog.builder()
                                .taskId(task.getId())
                                .attempt(task.getRetryCount() + 1)
                                .status("STARTED")
                                .startedAt(LocalDateTime.now())
                                .build();
        return logRepository.save(log);
    }

    @Override
    public void success(TaskExecutionLog log) {
        log.setStatus("COMPLETED");
        log.setFinishedAt(LocalDateTime.now());
        logRepository.save(log);
    }

    @Override
    public void failure(TaskExecutionLog log, String error) {
        log.setStatus("FAILED");
        log.setFinishedAt(LocalDateTime.now());
        log.setErrorMessage(error);
        logRepository.save(log);
    }
    
}
