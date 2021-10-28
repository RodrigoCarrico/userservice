package com.demo.userservice.config.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String ROUTINGKEY_PRINCIPAL = "ROUTINGKEY_PRINCIPAL";
    public static final String EXCHANGE_PRINCIPAL = "EXCHANGE_PRINCIPAL";
    public static final String QUEUE_PRINCIPAL = "QUEUE_PRINCIPAL";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_PRINCIPAL);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_PRINCIPAL);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_PRINCIPAL);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }


}
