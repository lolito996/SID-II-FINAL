package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.services.interfaces.UserService;
import com.example.demo.services.interfaces.RoleService;
import com.example.demo.services.interfaces.PermissionService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.model.postgres.User;
import com.example.demo.model.postgres.Role;
import com.example.demo.DTO.RoleDTO;
import com.example.demo.DTO.UserDTO;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    // Crear un usuario (método POST)
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(
        @RequestBody Map<String,String> userRequest
    ) {
        try {
            String username = userRequest.get("username");
            String password = userRequest.get("password");
            String name = userRequest.get("name");
            String identificationNumber = userRequest.get("identificationNumber");
            String birthdate = userRequest.get("birthdate");
            System.out.println("AAAAA");
            LocalDate birthDateParsed = LocalDate.parse(birthdate);
            userService.registerUser(username, password, name, identificationNumber, birthDateParsed, "");
            return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ocurrió un error al registrar el usuario: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los usuarios (método GET)
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return ResponseEntity.ok(users.stream().map(
                user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setUsername(user.getUsername());
                    dto.setIdentificationNumber(user.getIdentificationNumber());
                    dto.setBirthdate(user.getBirthdate());
                    List<Role> roles = userService.getRolesForUser(user);
                    dto.setRoles(roles.stream().map(
                        role -> {
                            RoleDTO roleDTO = new RoleDTO();
                            roleDTO.setId(role.getId());
                            roleDTO.setName(role.getName());
                            return roleDTO;
                        }
                    ).collect(Collectors.toList()));
                    return dto;
                }
            ).collect(Collectors.toList()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar un usuario por nombre de usuario (método GET)
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
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
}
