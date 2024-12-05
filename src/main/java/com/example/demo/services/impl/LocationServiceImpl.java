package com.example.demo.services.impl;

import com.example.demo.entity.LocationEntity;
import com.example.postmailcf.dto.LocationDTO;
import com.example.demo.exceptions.LocationNotFoundException;
import com.example.demo.repository.LocationRepository;
import com.example.demo.services.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {
        LocationEntity locationEntity = modelMapper.map(locationDTO, LocationEntity.class);
        var x = locationRepository.save(locationEntity);
        return modelMapper.map(x, LocationDTO.class);

    }

    @Override
    public void updateLocation(LocationDTO locationDTO) {
        Optional<LocationEntity> optionalLocation = locationRepository.findById(locationDTO.getId());
        if (optionalLocation.isPresent()) {
            LocationEntity locationEntity = modelMapper.map(locationDTO, LocationEntity.class);
            locationRepository.save(locationEntity);
        }
    }

    @Override
    public void deleteLocation(LocationDTO locationDTO) {
        locationRepository.deleteById(locationDTO.getId());
    }

    @Override
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public void deleteAllLocations() {
        locationRepository.deleteAll();
    }

    @Override
    public LocationDTO findLocationById(Long id) {
        return locationRepository.findById(id)
                .map(location -> modelMapper.map(location, LocationDTO.class))
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    @Override
    public List<LocationDTO> findAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(location -> modelMapper.map(location, LocationDTO.class))
                .toList();
    }
}
