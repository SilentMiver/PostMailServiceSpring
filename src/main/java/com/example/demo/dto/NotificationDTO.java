package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotificationDTO {
    private String message;
    private String type;
    private Long userId;
    private Long parcelId;
    private String status;
    private String timestamp;
}