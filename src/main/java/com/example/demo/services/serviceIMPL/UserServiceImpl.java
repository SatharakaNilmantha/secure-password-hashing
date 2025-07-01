package com.example.demo.services.serviceIMPL;

import com.example.demo.Exception.DuplicateException;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public  class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper ;



    @Override
    public String registerUser(UserDTO userDto) {
        // Check for duplicate email
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new DuplicateException("A user with this email already exists.");
        }

        // Hash the password before saving
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));



        // Map DTO to Entity
        User user = modelMapper.map(userDto, User.class);

        // Save the user
        userRepository.save(user);

        return "User registered successfully.";
    }




    @Override
    public String loginUser(UserDTO userDTO) {
        Optional<User> userOpt = userRepository.findByUsername(userDTO.getUsername());



        User user = userOpt.get();

        if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid username or password";
        }
    }
}