package com.example.blogapi.mapper;

import com.example.blogapi.dto.UserRegisterRequestDTO;
import com.example.blogapi.dto.UserResponseDTO;
import com.example.blogapi.dto.UserSummaryDTO;
import com.example.blogapi.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO userResponseDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setUsername(user.getUsername());
        return userResponseDTO;
    }

    public UserSummaryDTO userSummaryDTO(User user){
        UserSummaryDTO userSummaryDTO = new UserSummaryDTO();
        userSummaryDTO.setId(user.getId());
        userSummaryDTO.setUsername(user.getUsername());
        return userSummaryDTO;
    }

}
