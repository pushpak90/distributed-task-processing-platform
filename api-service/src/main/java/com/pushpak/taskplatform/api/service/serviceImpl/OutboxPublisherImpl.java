package com.pushpak.taskplatform.api.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pushpak.taskplatform.api.messaging.TaskProducer;
import com.pushpak.taskplatform.api.model.TaskOutbox;
import com.pushpak.taskplatform.api.repository.TaskOutboxRepository;
import com.pushpak.taskplatform.api.service.OutboxService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutboxPublisherImpl implements OutboxService {

    private final TaskOutboxRepository outboxRepository;
    private final TaskProducer taskProducer;

    @Scheduled(fixedDelay = 5000)
    @Override
    public void publishOutboxEvent() {
        List<TaskOutbox> events = outboxRepository.findByStatus("PENDING");

        for (TaskOutbox event : events) {

            try {
                taskProducer.sendTask(event.getTaskId());

                event.setStatus("SENT");
                event.setProcessedAt(LocalDateTime.now());

                outboxRepository.save(event);

            } catch (Exception e) {
                // keep as PENDING for retry
            }
        }
    }

}
