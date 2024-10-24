package com.example.demo.datafetchers;

import com.example.demo.dto.ParcelDTO;
import com.example.demo.services.ParcelService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;

@DgsComponent
public class ParcelDataFetcher {

    private final ParcelService parcelService;

    public ParcelDataFetcher(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @DgsQuery
    public List<ParcelDTO> allParcels() {
        return parcelService.findAllParcels();
    }

    @DgsQuery
    public ParcelDTO parcelById(Long id) {
        return parcelService.findParcelById(id);
    }

    @DgsMutation
    public ParcelDTO addParcel(Double weight, String size, String type, Long senderId, Long recipientId, Long locationId) {
        ParcelDTO parcelDTO = new ParcelDTO();
        parcelDTO.setWeight(weight);
        parcelDTO.setSize(size);
        parcelDTO.setType(type);
        parcelDTO.setSenderId(senderId);
        parcelDTO.setRecipientId(recipientId);
        parcelDTO.setLocationId(locationId);
        parcelService.addParcel(parcelDTO);
        return parcelDTO;
    }

    @DgsMutation
    public ParcelDTO updateParcel(Long id, Double weight, String size, String type) {
        ParcelDTO parcelDTO = parcelService.findParcelById(id);
        if (weight != null) parcelDTO.setWeight(weight);
        if (size != null) parcelDTO.setSize(size);
        if (type != null) parcelDTO.setType(type);
        parcelService.updateParcel(parcelDTO);
        return parcelDTO;
    }

    @DgsMutation
    public Boolean deleteParcel(Long id) {
        parcelService.deleteParcelById(id);
        return true;
    }
}