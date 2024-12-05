package com.example.demo.web.rest;


import com.example.postmailcf.dto.LocationDTO;
import com.example.demo.services.LocationService;
import com.example.postmailcf.controllers.LocationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController implements LocationApi {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public CollectionModel<EntityModel<LocationDTO>> getAllLocations() {
        List<EntityModel<LocationDTO>> locations = locationService.findAllLocations().stream()
                .map(location -> EntityModel.of(location,
                        linkTo(methodOn(LocationController.class).getLocationById(location.getId())).withSelfRel(),
                        linkTo(methodOn(LocationController.class).getAllLocations()).withRel("locations")))
                .collect(Collectors.toList());

        return CollectionModel.of(locations, linkTo(methodOn(LocationController.class).getAllLocations()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<LocationDTO> getLocationById(@PathVariable Long id) {
        LocationDTO location = locationService.findLocationById(id);
        return EntityModel.of(location,
                linkTo(methodOn(LocationController.class).getLocationById(id)).withSelfRel(),
                linkTo(methodOn(LocationController.class).getAllLocations()).withRel("locations"));
    }

    @PostMapping
    public ResponseEntity<?> createLocation(@RequestBody LocationDTO newLocation) {
        LocationDTO location = locationService.addLocation(newLocation);
        EntityModel<LocationDTO> entityModel = EntityModel.of(location,
                linkTo(methodOn(LocationController.class).getLocationById(location.getId())).withSelfRel());

        return ResponseEntity.created(
                        WebMvcLinkBuilder.linkTo(methodOn(LocationController.class).getLocationById(location.getId())).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@RequestBody LocationDTO updatedLocation, @PathVariable Long id) {
        locationService.updateLocation(updatedLocation);
        EntityModel<LocationDTO> entityModel = EntityModel.of(updatedLocation,
                linkTo(methodOn(LocationController.class).getLocationById(updatedLocation.getId())).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocationById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public EntityModel<LocationDTO> getLocationsByAddress(String address) {
        return null;
    }
}

