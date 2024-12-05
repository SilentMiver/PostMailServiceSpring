package com.example.demo.services;

import java.util.List;
import com.example.postmailcf.dto.UserDTO;
public interface UserService {
    void addUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(UserDTO userDTO);
    void deleteUserById(Long id);
    void deleteAllUsers();

    UserDTO findUserById(Long id);
    UserDTO findUserByUsername(String username);

    List<UserDTO> findAllUsers();
}