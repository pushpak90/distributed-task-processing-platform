package com.pushpak.taskplatform.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pushpak.taskplatform.api.model.TaskOutbox;

public interface TaskOutboxRepository extends JpaRepository<TaskOutbox, Long> {
    List<TaskOutbox> findByStatus(String status);
}
