package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.model.User;
import com.example.task_manager_backend.service.CustomUserDetailsService;
import com.example.task_manager_backend.util.JwtUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        return authenticateAndGenerateToken(authRequest.getEmail(), authRequest.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Add logic for saving the user (if not done already)
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return authenticateAndGenerateToken(authRequest.getEmail(), authRequest.getPassword());
    }

    private ResponseEntity<AuthResponse> authenticateAndGenerateToken(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new AuthResponse("Invalid credentials", null), HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(new AuthResponse(userDetails.getUsername(), jwt), HttpStatus.OK);
    }
}

@Data
@NoArgsConstructor
class AuthRequest {
    private String email;
    private String password;
}

@Data
@NoArgsConstructor
class AuthResponse {
    private String email;
    private String token;

    public AuthResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
