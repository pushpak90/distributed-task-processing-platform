package com.pushpak.taskplatform.api.dto;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private String taskType;
    private String payload;
}
