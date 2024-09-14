package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.model.User;
import com.example.task_manager_backend.service.CustomUserDetailsService;
import com.example.task_manager_backend.util.JwtUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid credentials", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return "User registered successfully";
    }
}
@Data
@NoArgsConstructor
class AuthRequest {
    private String email;
    private String password;

}
