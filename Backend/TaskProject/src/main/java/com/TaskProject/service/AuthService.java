package com.TaskProject.service;

import com.TaskProject.Entity.User;
import com.TaskProject.auth.AuthResponse;
import com.TaskProject.auth.LoginRequest;
import com.TaskProject.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);

}
