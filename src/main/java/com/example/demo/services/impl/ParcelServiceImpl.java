package com.example.demo.services.impl;


import com.example.demo.services.NotificationService;
import com.example.postmailcf.dto.ParcelDTO;
import com.example.demo.entity.ParcelEntity;
import com.example.demo.exceptions.ParcelNotFoundException;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.services.ParcelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelServiceImpl implements ParcelService {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.lost-parcels.exchange}")
    private String LOST_PARCELS_EXCHANGE;

    @Value("${rabbitmq.lost-parcels.key}")
    private String LOST_PARCELS_KEY;
    @Autowired
    private ParcelRepository parcelRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${rabbitmq.parcel-changed.exchange}")
    private String PARCEL_CHANGED_EXCHANGE;

    @Value("${rabbitmq.parcel-changed.key}")
    private String PARCEL_CHANGED_KEY;

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
            // Проверяем, потеряна ли посылка
            if ("LOST".equals(parcelEntity.getType())) {
                // Формируем сообщение о потерянной посылке
                String message = String.format("Parcel ID: %d marked as LOST", parcelDTO.getId());
                System.out.println("Отправка сообщения о потерянной посылке: " + message);

                // Отправляем сообщение в RabbitMQ
                rabbitTemplate.convertAndSend(LOST_PARCELS_EXCHANGE, LOST_PARCELS_KEY, message);
            }
            notificationService.sendNotification(parcelDTO);
            System.out.println("!!! Обновлен parcel " + parcelDTO);
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
