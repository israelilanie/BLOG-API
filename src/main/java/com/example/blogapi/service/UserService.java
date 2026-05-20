package com.example.blogapi.service;

import com.example.blogapi.dto.LoginRequestDTO;
import com.example.blogapi.dto.UserRegisterRequestDTO;
import com.example.blogapi.dto.UserResponseDTO;
import com.example.blogapi.dto.UserUpdateRequestDTO;
import com.example.blogapi.exception.DuplicateResourceException;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.mapper.UserMapper;
import com.example.blogapi.model.Role;
import com.example.blogapi.model.User;
import com.example.blogapi.repository.UserRepo;
import com.example.blogapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    public List<UserResponseDTO> getAll(){
        return userRepo.findAll().stream().map(userMapper::userResponseDTO).toList();
    }

    public UserResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO){

        logger.info("Attempting registration for email {}", userRegisterRequestDTO.getEmail());

        if(userRepo.findByEmail(userRegisterRequestDTO.getEmail()).isPresent()){
            logger.warn("Registration failed. Email already exists: {}", userRegisterRequestDTO.getEmail());
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setEmail(userRegisterRequestDTO.getEmail());
        user.setUsername(userRegisterRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));
        user.setRole(Role.ROLE_USER);
        User user1 = userRepo.save(user);

        logger.info("User successfully registered with id {}", user1.getId());

        emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
        return userMapper.userResponseDTO(user1);
    }

    public String login(LoginRequestDTO loginRequestDTO){
        logger.info("Attempt to login for email {}", loginRequestDTO.getEmail());
        User user = userRepo.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()->new ResourceNotFoundException("USER ",0L));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            logger.warn("Attempt to login for email {} , wrong password!", loginRequestDTO.getEmail());

            throw new RuntimeException("Wrong Credentials!");
        }
        logger.info("Attempt to login Successful for email {}", loginRequestDTO.getEmail());
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    public UserResponseDTO getUserById(Long id){
        User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User",id));
        return userMapper.userResponseDTO(user);
    }

    public void  deleteById(Long id){
        userRepo.deleteById(id);
    }

    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO userUpdateRequestDTO){
        User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User",id));
        user.setPassword(userUpdateRequestDTO.getPassword());
        user.setUsername(userUpdateRequestDTO.getUsername());
        User user1 = userRepo.save(user);
        return userMapper.userResponseDTO(user1);
    }

    public Page<UserResponseDTO> getAllUser(Pageable pageable){
        return userRepo.findAll(pageable).map(userMapper::userResponseDTO);
    }

    public UserResponseDTO getMe(User currentUser){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()-> new ResourceNotFoundException("User",currentUser.getId()));
        return userMapper.userResponseDTO(user);
    }

    public void deleteMe(User currentUser){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()-> new ResourceNotFoundException("User",currentUser.getId()));
        userRepo.delete(user);
    }

    public UserResponseDTO updateMe(User currentUser, UserUpdateRequestDTO userUpdateRequestDTO){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()-> new ResourceNotFoundException("User", currentUser.getId()));
        user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));
        user.setUsername(userUpdateRequestDTO.getUsername());
        User user1 = userRepo.save(user);
        return userMapper.userResponseDTO(user1);
    }


}
