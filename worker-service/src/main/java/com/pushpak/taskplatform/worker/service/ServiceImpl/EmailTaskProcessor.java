package com.pushpak.taskplatform.worker.service.ServiceImpl;

import org.springframework.stereotype.Service;

import com.pushpak.taskplatform.worker.model.Task;
import com.pushpak.taskplatform.worker.service.TaskProcessor;

@Service
public class EmailTaskProcessor  implements TaskProcessor{

    @Override
    public void process(Task task) {
        System.out.println("Sending email with payload " + task.getPayload());
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException exception){
            Thread.currentThread().interrupt();
        }
    }
    
}
