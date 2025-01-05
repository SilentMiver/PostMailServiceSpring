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

    // Новые параметры из application.properties
    @Value("${rabbitmq.lost-parcels.queue}")
    private String LOST_PARCELS_QUEUE;

    @Value("${rabbitmq.lost-parcels.exchange}")
    private String LOST_PARCELS_EXCHANGE;

    @Value("${rabbitmq.lost-parcels.key}")
    private String LOST_PARCELS_KEY;

    @Bean
    public Queue lostParcelsQueue() {
        return new Queue(LOST_PARCELS_QUEUE, false); // Очередь не сохраняется после перезагрузки сервера
    }

    @Bean
    public TopicExchange lostParcelsExchange() {
        return new TopicExchange(LOST_PARCELS_EXCHANGE, false, false);
    }

    @Bean
    public Binding lostParcelsBinding(Queue lostParcelsQueue, TopicExchange lostParcelsExchange) {
        return BindingBuilder
                .bind(lostParcelsQueue)
                .to(lostParcelsExchange)
                .with(LOST_PARCELS_KEY); // Связываем ключ маршрутизации с очередью
    }

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