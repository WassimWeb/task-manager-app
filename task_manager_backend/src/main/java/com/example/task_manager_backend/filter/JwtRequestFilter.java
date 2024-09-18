package com.example.task_manager_backend.filter;

import com.example.task_manager_backend.service.CustomUserDetailsService;
import com.example.task_manager_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
    
        String username = null;
        String jwt = null;
    
        // Skip JWT validation for /auth/login endpoint
        String requestURI = request.getRequestURI();
        if ("/auth/login".equals(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
    
        // Extract JWT token from Authorization header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Extract token after 'Bearer '
            try {
                username = jwtUtil.extractUsername(jwt); // Get username from token
            } catch (Exception e) {
                logger.error("JWT token extraction failed: " + e.getMessage());  // Log failure
            }
        }
    
        // Check if username is extracted and no authentication context is set
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
    
            // Validate the token with the entire UserDetails object
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);  // Set security context
            } else {
                logger.error("JWT token validation failed.");
            }
        }
        
        // Continue the chain
        chain.doFilter(request, response);
    }
}
