package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParcelLocationChanged implements Serializable {
    private Long id;
    private String address;

}
