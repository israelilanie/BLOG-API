package com.example.blogapi.controller;

import com.example.blogapi.dto.LoginRequestDTO;
import com.example.blogapi.dto.UserRegisterRequestDTO;
import com.example.blogapi.dto.UserResponseDTO;
import com.example.blogapi.dto.UserUpdateRequestDTO;
import com.example.blogapi.model.User;
import com.example.blogapi.security.JwtUtil;
import com.example.blogapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "Manage blog posts")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new User")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "401",description = "Bad Request!")}
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody @Valid UserRegisterRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(dto));
    }

    @Operation(summary = "User login")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Login Successful"),
            @ApiResponse(responseCode = "401",description = "Bad Request!")}
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDTO));
    }

    @Operation(summary = "Get list of all users")
    @ApiResponse(responseCode = "200", description = "List Found!")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAll(){
        List<UserResponseDTO> list = userService.getAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "User Found!"),
            @ApiResponse(responseCode = "404",description = "User not found")}
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Updating an existing User")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404",description = "User not found"),
            @ApiResponse(responseCode = "400",description = "Bad Request!")}
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO dto) {

        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @Operation(summary = "Deleting an existing User")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "User deleted!"),
            @ApiResponse(responseCode = "404",description = "User not found")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Page of Users ")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Pages Found!"),
            @ApiResponse(responseCode = "400",description = "Bad Request!")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getUsers(Pageable pageable){
        return ResponseEntity.ok(userService.getAllUser(pageable));
    }

    @Operation(summary = "Get Actual Authenticated user")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "User Found!"),
            @ApiResponse(responseCode = "404",description = "User not found")}
    )
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getMe(user));
    }

    @Operation(summary = "Update Actual Authenticated user")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Updated!"),
            @ApiResponse(responseCode = "400",description = "Bad Request!")}
    )
    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateMe(@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO , @AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.updateMe(user,userUpdateRequestDTO));
    }

    @Operation(summary = "Delete Actual Authenticated user")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "204", description = "Deleted!"),
            @ApiResponse(responseCode = "400",description = "User not found")}
    )
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal User user){
        userService.deleteMe(user);
        return ResponseEntity.noContent().build();
    }
}
