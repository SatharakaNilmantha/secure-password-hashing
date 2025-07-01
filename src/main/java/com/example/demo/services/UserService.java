package com.example.demo.services;

import com.example.demo.dto.UserDTO;

public interface UserService {

    String registerUser(UserDTO userDTO);

    String loginUser(UserDTO userDTO);
}
