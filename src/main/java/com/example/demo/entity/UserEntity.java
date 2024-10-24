package com.example.demo.entity;

import com.example.demo.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false, length = 32)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<ParcelEntity> sentParcels;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipient")
    private List<ParcelEntity> receivedParcels;
}
