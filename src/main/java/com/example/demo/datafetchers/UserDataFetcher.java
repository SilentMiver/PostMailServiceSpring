package com.example.demo.datafetchers;

import com.example.demo.dto.UserDTO;
import com.example.demo.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;

@DgsComponent
public class UserDataFetcher {

    private final UserService userService;

    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @DgsQuery
    public List<UserDTO> allUsers() {
        return userService.findAllUsers();
    }

    @DgsQuery
    public UserDTO userById(Long id) {
        return userService.findUserById(id);
    }

    @DgsMutation
    public UserDTO addUser(String username, String email, String password, String phone) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setPassword(password); // Не забудьте добавить хеширование
        userDTO.setPhone(phone);
        userService.addUser(userDTO);
        return userDTO;
    }

    @DgsMutation
    public UserDTO updateUser(Long id, String username, String email, String password, String phone) {
        UserDTO userDTO = userService.findUserById(id);
        if (username != null) userDTO.setUsername(username);
        if (email != null) userDTO.setEmail(email);
        if (password != null) userDTO.setPassword(password); // Не забудьте добавить хеширование
        if (phone != null) userDTO.setPhone(phone);
        userService.updateUser(userDTO);
        return userDTO;
    }

    @DgsMutation
    public Boolean deleteUser(Long id) {
        userService.deleteUserById(id);
        return true;
    }
}
