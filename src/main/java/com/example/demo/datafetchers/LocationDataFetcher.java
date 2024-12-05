package com.example.demo.datafetchers;

import com.example.postmailcf.dto.LocationDTO;
import com.example.demo.services.LocationService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;

@DgsComponent
public class LocationDataFetcher {

    private final LocationService locationService;

    public LocationDataFetcher(LocationService locationService) {
        this.locationService = locationService;
    }

    @DgsQuery
    public List<LocationDTO> allLocations() {
        return locationService.findAllLocations();
    }

    @DgsQuery
    public LocationDTO locationById(Long id) {
        return locationService.findLocationById(id);
    }

    @DgsMutation
    public LocationDTO addLocation(String address, String name) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setAddress(address);
        locationDTO.setName(name);
        locationService.addLocation(locationDTO);
        return locationDTO;
    }

    @DgsMutation
    public LocationDTO updateLocation(Long id, String address, String name) {
        LocationDTO locationDTO = locationService.findLocationById(id);
        if (address != null) locationDTO.setAddress(address);
        if (name != null) locationDTO.setName(name);
        locationService.updateLocation(locationDTO);
        return locationDTO;
    }

    @DgsMutation
    public Boolean deleteLocation(Long id) {
        locationService.deleteLocationById(id);
        return true;
    }
}
