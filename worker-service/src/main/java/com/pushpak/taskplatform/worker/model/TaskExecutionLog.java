package com.pushpak.taskplatform.worker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_execution_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskExecutionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private int attempt;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    @Column(columnDefinition = "Text")
    private String errorMessage;
}
