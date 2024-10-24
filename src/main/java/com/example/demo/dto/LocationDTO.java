package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocationDTO {
    private Long id;
    private String address;
    private String name; // Название транспортного пункта
}
