package com.example.demo.services;

import com.example.demo.dto.LocationDTO;

import java.util.List;

public interface LocationService {
    LocationDTO addLocation(LocationDTO locationDTO);

    void updateLocation(LocationDTO locationDTO);
    void deleteLocation(LocationDTO locationDTO);
    void deleteLocationById(Long id);
    void deleteAllLocations();

    LocationDTO findLocationById(Long id);
    List<LocationDTO> findAllLocations();
}
