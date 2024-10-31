package com.example.demo.services;

import com.example.demo.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationDTO notification);
    List<NotificationDTO> getNotificationsByUserId(Long userId);
}