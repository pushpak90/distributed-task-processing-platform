package com.pushpak.taskplatform.worker.service;

import org.springframework.stereotype.Component;

import com.pushpak.taskplatform.worker.service.ServiceImpl.EmailTaskProcessor;

@Component
public class TaskProcessorFactory {
    private final EmailTaskProcessor emailTaskProcessor;

    public TaskProcessorFactory(EmailTaskProcessor emailTaskProcessor){
        this.emailTaskProcessor = emailTaskProcessor;
    }

    public TaskProcessor getProcessor(String taskType){
        if("EMAIL".equalsIgnoreCase(taskType)){
            return emailTaskProcessor;
        }
        throw new RuntimeException("Unsupported task type : " + taskType);
    }
}
