package com.pushpak.taskplatform.worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pushpak.taskplatform.worker.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
    
}
