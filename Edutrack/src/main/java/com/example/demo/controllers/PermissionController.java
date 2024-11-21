package com.example.demo.controllers;


import com.example.demo.DTO.PermissionDTO;
import com.example.demo.model.postgres.Permission;
import com.example.demo.services.interfaces.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permissions")
    @PreAuthorize("hasRole('MANAGE_PERMISSIONS')")
    public List<PermissionDTO> getAllPermissions() {
        List<Permission> permissions = permissionService.showAllPermissions();
        return permissions.stream()
                .map(permission -> {
                    PermissionDTO dto = new PermissionDTO();
                    dto.setId(permission.getId());
                    dto.setName(permission.getName());
                    dto.setDescription(permission.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/permissions/{name}")
    @PreAuthorize("hasRole('MANAGE_PERMISSIONS')")
    public ResponseEntity<PermissionDTO> getPermissionByName(@PathVariable("name") String name) {
        Permission permission = permissionService.findByName(name);
        if (permission == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        PermissionDTO dto = new PermissionDTO();
        dto.setId(permission.getId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/permission")
    @PreAuthorize("hasRole('MANAGE_PERMISSIONS')")
    public ResponseEntity<String> createPermission(@RequestBody PermissionDTO permissionDTO) {
        try {
            Permission permission = new Permission();
            permission.setName(permissionDTO.getName());
            permission.setDescription(permissionDTO.getDescription());

            permissionService.savePermission(permission);
            return ResponseEntity.status(HttpStatus.CREATED).body("Permission created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating permission: " + e.getMessage());
        }
    }

    @PutMapping("/permission/{id}")
    @PreAuthorize("hasRole('MANAGE_PERMISSIONS')")
    public ResponseEntity<String> updatePermission(@PathVariable("id") Integer id, @RequestBody PermissionDTO permissionDTO) {
        Permission existingPermission = permissionService.findById(id);
        if (existingPermission == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permission not found with ID: " + id);
        }

        existingPermission.setName(permissionDTO.getName());
        existingPermission.setDescription(permissionDTO.getDescription());

        permissionService.savePermission(existingPermission);
        return ResponseEntity.ok("Permission updated successfully");
    }

    @DeleteMapping("/permission/{id}")
    @PreAuthorize("hasRole('MANAGE_PERMISSIONS')")
    public ResponseEntity<String> deletePermission(@PathVariable("id") Integer id) {
        Permission permission = permissionService.findById(id);
        if (permission == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Permission not found with ID: " + id);
        }

        permissionService.deletePermission(permission);
        return ResponseEntity.ok("Permission deleted successfully");
    }
}
