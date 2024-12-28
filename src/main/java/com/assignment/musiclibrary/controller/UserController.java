package com.assignment.musiclibrary.controller;

import com.assignment.musiclibrary.api.ApiResponse;
import com.assignment.musiclibrary.dto.AddUserRequest;
import com.assignment.musiclibrary.dto.LoginRequest;
import com.assignment.musiclibrary.dto.UpdatePasswordRequest;
import com.assignment.musiclibrary.dto.UserSignupRequest;
import com.assignment.musiclibrary.model.User;
import com.assignment.musiclibrary.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody UserSignupRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(201).body(createResponse(201, "User created successfully."));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.authenticate(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(404).body(createResponse(404, "User not found."));
        }
        String token = "generate_token_here"; // Replace with actual JWT generation
        return ResponseEntity.ok(createResponse(200, "Login successful.", token));
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(@RequestParam(defaultValue = "5") int limit,
                                           @RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(required = false) String role) {
        List<User> users = userService.getAllUsers(limit, offset, role);
        return ResponseEntity.ok(createResponse(200, "Users retrieved successfully.", users));
    }

    @PostMapping("/users/add-user")
    public ResponseEntity<Object> addUser(@Valid @RequestBody AddUserRequest request) {
        userService.addUser(request);
        return ResponseEntity.status(201).body(createResponse(201, "User created successfully."));
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout() {
        // Implement token invalidation logic
        return ResponseEntity.ok(createResponse(200, "User logged out successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID userId, @RequestHeader("Authorization") String token) {
        if (userService.deleteUser(userId, token)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "User deleted successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "User not found.", null));
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request, @RequestHeader("Authorization") String token) {
        boolean isUpdated = userService.updatePassword(request, token);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "User not found.", null));
    }



    private Map<String, Object> createResponse(int status, String message) {
        return createResponse(status, message, null);
    }

    private Map<String, Object> createResponse(int status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        response.put("error", null);
        return response;
    }
}
