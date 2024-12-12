package com.example.demo.services;

import com.example.demo.dto.NotificationDTO;
import com.example.postmailcf.dto.ParcelDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${rabbitmq.notification.exchange}")
    private String NOTIFICATION_EXCHANGE;

    @Value("${rabbitmq.notification.key}")
    private String NOTIFICATION_KEY;


    @Override
    public void sendNotification(NotificationDTO notification) {
        notification.setTimestamp(LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, NOTIFICATION_KEY, notification);
    }

    public void sendNotification(ParcelDTO notification) {
        try {
            String message = objectMapper.writeValueAsString(notification);
            rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, NOTIFICATION_KEY, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendParcelNotification(String exchange, String routingKey, ParcelDTO parcel) {
        try {
            String message = objectMapper.writeValueAsString(parcel);
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        // This is a placeholder. In a real application, you would typically
        // retrieve notifications from a database or message store
        return new ArrayList<>();
    }
}