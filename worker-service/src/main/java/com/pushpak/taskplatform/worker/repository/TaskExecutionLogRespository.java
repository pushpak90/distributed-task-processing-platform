package com.pushpak.taskplatform.worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pushpak.taskplatform.worker.model.TaskExecutionLog;

public interface TaskExecutionLogRespository extends JpaRepository<TaskExecutionLog, Long>{
    
}
