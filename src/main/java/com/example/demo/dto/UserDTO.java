package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password; // Лучше хранить зашифрованным
    private String phone;
    private String firstName;
    private String lastName;
}
