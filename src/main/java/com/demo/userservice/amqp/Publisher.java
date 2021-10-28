package com.demo.userservice.amqp;

import com.demo.userservice.utils.DomainCommand;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import static com.demo.userservice.config.amqp.MessagingConfig.EXCHANGE_PRINCIPAL;
import static com.demo.userservice.config.amqp.MessagingConfig.ROUTINGKEY_PRINCIPAL;

@AllArgsConstructor
@Component
public class Publisher {

    @Autowired
    private RabbitTemplate template;

    public void dispatch(DomainCommand event) {
        Map<String, Object> mapHeader = new HashMap<>();
        mapHeader.put("class", event.getClass().getSimpleName());
        var headers = new MessageHeaders(mapHeader);
        var message = MessageBuilder.createMessage(event, headers);
        template.convertAndSend(EXCHANGE_PRINCIPAL, ROUTINGKEY_PRINCIPAL, message);
   }

}
