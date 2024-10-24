package com.example.demo.repository;

import com.example.demo.entity.ParcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {}
