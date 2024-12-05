package com.example.demo.web.rest;

import com.example.demo.services.UserService;
import com.example.postmailcf.controllers.UserApi;
import com.example.postmailcf.dto.UserDTO;
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
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<EntityModel<UserDTO>> getAllUsers() {
        List<EntityModel<UserDTO>> users = userService.findAllUsers().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).getAllUsers()).withRel("users")))
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findUserById(id);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO newUser) {
        userService.addUser(newUser);
        EntityModel<UserDTO> entityModel = EntityModel.of(newUser,
                linkTo(methodOn(UserController.class).getUserById(newUser.getId())).withSelfRel());

        return ResponseEntity.created(
                        WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(newUser.getId())).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO updatedUser, @PathVariable Long id) {
        userService.updateUser(updatedUser);
        EntityModel<UserDTO> entityModel = EntityModel.of(updatedUser,
                linkTo(methodOn(UserController.class).getUserById(updatedUser.getId())).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public CollectionModel<EntityModel<UserDTO>> getUsersByEmail(String email) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<UserDTO>> getUsersByPhone(String phone) {
        return null;
    }
}
