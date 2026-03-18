package com.pushpak.taskplatform.worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pushpak.taskplatform.worker.enums.TaskStatus;
import com.pushpak.taskplatform.worker.model.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, Long>{
    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.status = :processing WHERE t.id = :id AND t.status = :pending")
    int markAsProcessingIfPending(Long id, TaskStatus pending, TaskStatus processing);

}
