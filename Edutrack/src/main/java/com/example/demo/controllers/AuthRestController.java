package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.JwtResponseDTO;
import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.model.postgres.User;
import com.example.demo.services.impl.JwtServiceImpl;
import com.example.demo.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    
    @Autowired
    private JwtServiceImpl jwtService; 
    
    @Autowired
    private UserServiceImpl userService; 

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );
            
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            User user = userService.findByUsername(loginRequest.getUsername());
            String jwt = jwtService.generateToken(userDetails);
            // Obtén los roles del usuario
            List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
         // Verifica si el curso es null
            Integer courseId = (user.getCourse() != null) ? user.getCourse().getId() : null;
            JwtResponseDTO jwtResponse = new JwtResponseDTO(
                    jwt,
                    user.getId(), // Asegúrate de que UserDetails tenga un método getId()
                    user.getUsername(),
                    courseId, // Asegúrate de que UserDetails tenga un método getCourseId()
                    roles
                );

                return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de autenticación");
        }
    }
    
}
