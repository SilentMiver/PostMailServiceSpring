package com.example.demo.services;

import com.example.demo.dto.NotificationDTO;
import com.example.postmailcf.dto.ParcelDTO;

import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationDTO notification);
    void sendNotification(ParcelDTO notification);
    void sendParcelNotification(String exchange, String routingKey, ParcelDTO parcel);
    List<NotificationDTO> getNotificationsByUserId(Long userId);

}