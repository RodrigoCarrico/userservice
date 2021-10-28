package com.demo.userservice.amqp;

import com.demo.userservice.config.amqp.MessagingConfig;
import com.demo.userservice.domain.events.UserCreatedEvent;
import com.demo.userservice.domain.events.UserUpdatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserSubscriber {

    @RabbitListener(queues = MessagingConfig.QUEUE_PRINCIPAL)
    public void userCreated(Message message) {
        var event = (Map<String, Object>) message.getPayload();
        var classe = (Map<String, Object>) event.get("headers");
        switch (classe.get("class").toString()) {
            case UserCreatedEvent.NAME:
                System.out.println("Create: " + event.get("payload"));
                break;
            case UserUpdatedEvent.NAME:
                System.out.println("Updated: " + event.get("payload"));
                break;
            default:
                System.out.println("Nothing...");
        }
    }
}
