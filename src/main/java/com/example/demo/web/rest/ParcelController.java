package com.example.demo.web.rest;

import com.example.postmailcf.controllers.ParcelApi;
import com.example.postmailcf.dto.ParcelDTO;
import com.example.demo.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController implements ParcelApi {

    private final ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @GetMapping
    public CollectionModel<EntityModel<ParcelDTO>> getAllParcels() {
        List<EntityModel<ParcelDTO>> parcels = parcelService.findAllParcels().stream()
                .map(parcel -> EntityModel.of(parcel,
                        linkTo(methodOn(ParcelController.class).getParcelById(parcel.getId())).withSelfRel(),
                        linkTo(methodOn(ParcelController.class).getAllParcels()).withRel("parcels")))
                .collect(Collectors.toList());

        return CollectionModel.of(parcels, linkTo(methodOn(ParcelController.class).getAllParcels()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ParcelDTO> getParcelById(@PathVariable Long id) {
        ParcelDTO parcel = parcelService.findParcelById(id);
        return EntityModel.of(parcel,
                linkTo(methodOn(ParcelController.class).getParcelById(id)).withSelfRel(),
                linkTo(methodOn(ParcelController.class).getAllParcels()).withRel("parcels"));
    }

    @PostMapping
    public ResponseEntity<?> createParcel(@RequestBody ParcelDTO newParcel) {
        parcelService.addParcel(newParcel);
        EntityModel<ParcelDTO> entityModel = EntityModel.of(newParcel,
                linkTo(methodOn(ParcelController.class).getParcelById(newParcel.getId())).withSelfRel());

        return ResponseEntity.created(
                        WebMvcLinkBuilder.linkTo(methodOn(ParcelController.class).getParcelById(newParcel.getId())).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParcel(@RequestBody ParcelDTO updatedParcel, @PathVariable Long id) {
        parcelService.updateParcel(updatedParcel);
        EntityModel<ParcelDTO> entityModel = EntityModel.of(updatedParcel,
                linkTo(methodOn(ParcelController.class).getParcelById(updatedParcel.getId())).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParcel(@PathVariable Long id) {
        parcelService.deleteParcelById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public CollectionModel<EntityModel<ParcelDTO>> getParcelsBySender(Long senderId) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<ParcelDTO>> getParcelsByMinWeight(Double minWeight) {
        return null;
    }
}
