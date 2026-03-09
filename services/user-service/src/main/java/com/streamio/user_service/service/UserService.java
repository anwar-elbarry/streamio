package com.streamio.user_service.service;

import com.streamio.user_service.dto.UserRequest;
import com.streamio.user_service.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);

    UserResponse getUserByEmail(String email);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
