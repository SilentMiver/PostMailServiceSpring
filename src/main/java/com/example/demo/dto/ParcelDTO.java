package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParcelDTO {
    private Long id;
    private Double weight;
    private String size; // SMALL, MEDIUM, LARGE
    private String type; // STANDARD, EXPRESS
    private Long senderId; // ID отправителя
    private Long recipientId; // ID получателя
    private Long locationId; // ID транспортного пункта
}