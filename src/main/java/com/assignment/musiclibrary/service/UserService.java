package com.assignment.musiclibrary.service;

import com.assignment.musiclibrary.dto.AddUserRequest;
import com.assignment.musiclibrary.dto.UpdatePasswordRequest;
import com.assignment.musiclibrary.dto.UserSignupRequest;
import com.assignment.musiclibrary.exceptions.EmailAlreadyExistsException;
import com.assignment.musiclibrary.exceptions.ForbiddenOperationException;
import com.assignment.musiclibrary.model.Role;
import com.assignment.musiclibrary.model.User;
import com.assignment.musiclibrary.repository.UserRepository;
import com.assignment.musiclibrary.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void createUser(UserSignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.VIEWER);
        userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(null);
    }

    public List<User> getAllUsers(int limit, int offset, String role) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return role != null ? userRepository.findByRole(role, pageable) : userRepository.findAll(pageable).getContent();
    }

    public void addUser(AddUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            throw new ForbiddenOperationException();
        }
        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.valueOf(request.getRole().toUpperCase()));
        userRepository.save(user);
    }

    public boolean deleteUser(UUID userId, String token) {
        if (!jwtUtil.isAdmin(token)) {
            return false; // Unauthorized access
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean updatePassword(UpdatePasswordRequest request, String token) {
        UUID userId = UUID.fromString(jwtUtil.getUserIdFromToken(token));
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent() && passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())) {
            user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
}
