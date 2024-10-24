package com.example.demo.services.impl;


import com.example.demo.dto.ParcelDTO;
import com.example.demo.entity.ParcelEntity;
import com.example.demo.exceptions.ParcelNotFoundException;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.services.ParcelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ParcelServiceImpl(ParcelRepository parcelRepository, ModelMapper modelMapper) {
        this.parcelRepository = parcelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addParcel(ParcelDTO parcelDTO) {
        ParcelEntity parcelEntity = modelMapper.map(parcelDTO, ParcelEntity.class);
        parcelRepository.save(parcelEntity);
    }

    @Override
    public void updateParcel(ParcelDTO parcelDTO) {
        Optional<ParcelEntity> optionalParcel = parcelRepository.findById(parcelDTO.getId());
        if (optionalParcel.isPresent()) {
            ParcelEntity parcelEntity = modelMapper.map(parcelDTO, ParcelEntity.class);
            parcelRepository.save(parcelEntity);
        }
    }

    @Override
    public void deleteParcel(ParcelDTO parcelDTO) {
        parcelRepository.deleteById(parcelDTO.getId());
    }

    @Override
    public void deleteParcelById(Long id) {
        parcelRepository.deleteById(id);
    }

    @Override
    public void deleteAllParcels() {
        parcelRepository.deleteAll();
    }

    @Override
    public ParcelDTO findParcelById(Long id) {
        return parcelRepository.findById(id)
                .map(parcel -> modelMapper.map(parcel, ParcelDTO.class))
                .orElseThrow(() -> new ParcelNotFoundException(id));
    }

    @Override
    public List<ParcelDTO> findAllParcels() {
        return parcelRepository.findAll()
                .stream()
                .map(parcel -> modelMapper.map(parcel, ParcelDTO.class))
                .toList();
    }
}
