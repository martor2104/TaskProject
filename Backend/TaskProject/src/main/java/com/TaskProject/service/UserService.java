package com.TaskProject.service;

import com.TaskProject.DTO.UserDTO;
import com.TaskProject.Entity.SystemRole;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDTO, SystemRole systemRole);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
