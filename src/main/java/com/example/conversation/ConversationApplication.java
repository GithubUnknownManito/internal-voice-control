package com.example.conversation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

@SpringBootApplication
public class ConversationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConversationApplication.class, args);
    }

}
