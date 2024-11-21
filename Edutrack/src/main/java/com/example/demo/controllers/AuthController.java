package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.services.interfaces.UserService;
import com.example.demo.services.impl.JwtServiceImpl;
import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.model.postgres.User;
import com.example.demo.DTO.JwtResponseDTO;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/auth") 
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtServiceImpl jwtService; 

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String jwt = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponseDTO(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de autenticación");
        }
    }

    // Página de registro de usuario
    @GetMapping("/register")
    public String register() {
        return "auth/register"; 
    }


    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
    //     try {
    //         authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(
    //                 loginRequest.getUsername(), 
    //                 loginRequest.getPassword()
    //             )
    //         );

    //         UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
    //         String jwt = jwtService.generateToken(userDetails);

    //         return ResponseEntity.ok(new JwtResponseDTO(jwt));
    //     } catch (BadCredentialsException e) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de autenticación");
    //     }
    // }



    // Crear un usuario (método POST)
    @PostMapping("/register")
    @PreAuthorize("hasRole('MANAGE_USERS')")
    public ResponseEntity<String> registerUser(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String name,
        @RequestParam String identificationNumber,
        @RequestParam String birthdate 
        ) {

        try {
            LocalDate birthDateParsed = LocalDate.parse(birthdate);
            userService.registerUser(username, password, name, identificationNumber, birthDateParsed, "");
            return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error al registrar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('MANAGE_USERS')")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByUsername")
    @PreAuthorize("hasRole('MANAGE_USERS')")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        try {
            User user = userService.findByUsername(username);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/validate")
    @ResponseBody
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        try {
            // Manejo del caso en que no hay token o no comienza con "Bearer "
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = tokenHeader.substring(7);
            String username = jwtService.extractUserName(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtService.validateToken(token, userDetails)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
