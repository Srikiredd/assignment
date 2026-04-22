package com.swaroop.Scheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Scheduled(fixedRate = 300000)
    public void run() {
        System.out.println("Running notification scheduler...");
    }
}