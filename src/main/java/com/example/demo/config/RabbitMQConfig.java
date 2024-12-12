package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.notification.queue}")
    private String NOTIFICATION_QUEUE;

    @Value("${rabbitmq.notification.exchange}")
    private String NOTIFICATION_EXCHANGE;

    @Value("${rabbitmq.notification.key}")
    private String NOTIFICATION_KEY;

    @Value("${rabbitmq.parcel-changed.queue}")
    private String PARCEL_CHANGED_QUEUE;

    @Value("${rabbitmq.parcel-changed.exchange}")
    private String PARCEL_CHANGED_EXCHANGE;

    @Value("${rabbitmq.parcel-changed.key}")
    private String PARCEL_CHANGED_KEY;

    @Bean
    public Queue parcelChangedQueue() {
        return new Queue(PARCEL_CHANGED_QUEUE, false);
    }


    @Bean
    public TopicExchange parcelChangedExchange() {
        return new TopicExchange(PARCEL_CHANGED_QUEUE, false, false);
    }


    @Bean
    public Binding parcelChangedBinding(Queue parcelChangedQueue, TopicExchange parcelChangedExchange) {
        return BindingBuilder.bind(parcelChangedQueue)
                .to(parcelChangedExchange)
                .with(PARCEL_CHANGED_KEY);
    }


    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, false);
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE, false, false);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_KEY);
    }
}