

package com.example.demo.web;

import com.example.demo.web.rest.LocationController;
import com.example.demo.web.rest.ParcelController;
import com.example.demo.web.rest.UserController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MainController {

    @GetMapping("/")
    public String root() {
        return "Welcome to the Parcel Management System!";
    }

    @GetMapping("/api")
    public RepresentationModel<?> getLinks() {
        RepresentationModel<?> links = new RepresentationModel<>();

        links.add(Link.of("/").withSelfRel());

        links.add(linkTo(methodOn(UserController.class).getAllUsers())
                        .withRel("All Users")
                        .withType("GET")
                        .withTitle("Просмотр всех пользователей"))
                .add(linkTo(methodOn(ParcelController.class).getAllParcels())
                        .withRel("All Parcels")
                        .withType("GET")
                        .withTitle("Просмотр всех посылок"))
                .add(linkTo(methodOn(LocationController.class).getAllLocations())
                        .withRel("All Locations")
                        .withType("GET")
                        .withTitle("Просмотр всех точек"));
        return links;
    }
}
