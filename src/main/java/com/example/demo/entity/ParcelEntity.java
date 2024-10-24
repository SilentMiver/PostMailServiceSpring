package com.example.demo.entity;

import com.example.demo.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parcels")
@Getter @Setter @NoArgsConstructor
public class ParcelEntity extends BaseEntity {
    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "size", nullable = false)
    private String size; // Например, SMALL, MEDIUM, LARGE

    @Column(name = "type", nullable = false)
    private String type; // Например, STANDARD, EXPRESS

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private UserEntity recipient;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;
}

