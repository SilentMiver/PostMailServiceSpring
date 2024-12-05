package com.example.demo.services;

import com.example.postmailcf.dto.ParcelDTO;

import java.util.List;


public interface ParcelService {
    void addParcel(ParcelDTO parcelDTO);

    void updateParcel(ParcelDTO parcelDTO);

    void deleteParcel(ParcelDTO parcelDTO);

    void deleteParcelById(Long id);

    void deleteAllParcels();

    ParcelDTO findParcelById(Long id);

    List<ParcelDTO> findAllParcels();
}