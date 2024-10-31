package com.example.demo.services.impl;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.services.NotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public NotificationServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendNotification(NotificationDTO notification) {
        notification.setTimestamp(LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, notification);
    }

    @Override
    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        // This is a placeholder. In a real application, you would typically
        // retrieve notifications from a database or message store
        return new ArrayList<>();
    }
}