package com.example.demo.entity;

import com.example.demo.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "locations")
@Getter @Setter @NoArgsConstructor
public class LocationEntity extends BaseEntity {
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "name", nullable = false)
    private String name; // Название транспортного пункта

    @OneToMany(mappedBy = "location")
    private List<ParcelEntity> parcels;
}

