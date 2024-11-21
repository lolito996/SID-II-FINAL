package com.example.demo.controllers;

import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.Rolepermission;
import com.example.demo.model.postgres.RolepermissionId;
import com.example.demo.model.postgres.User;
import com.example.demo.model.postgres.Userrole;
import com.example.demo.DTO.PermissionDTO;
import com.example.demo.DTO.RoleDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.model.postgres.Permission;
import com.example.demo.model.postgres.UserroleId;
import com.example.demo.services.impl.UserServiceImpl;
import com.example.demo.services.interfaces.PermissionService;
import com.example.demo.services.interfaces.RoleService;

import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class UserRoleRestController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    // Asignar un rol a un usuario
    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<String> assignRole(
            @PathVariable("userId") Integer userId,
            @PathVariable("roleId") Integer roleId) {
        try {
            User user = userService.findById(userId);
            Role role = roleService.findById(roleId);

            if (user == null || role == null) {
                return ResponseEntity.badRequest().body("Usuario o rol no encontrado");
            }

            // Crear y asignar el rol
            Userrole userRole = new Userrole();
            UserroleId userRoleId = new UserroleId();
            userRoleId.setUserCourseId(user.getId());
            userRoleId.setRoleId(role.getId());
            userRole.setId(userRoleId);
            userRole.setUser(user);
            userRole.setRole(role);

            userService.assignRole(userRole);
            return ResponseEntity.ok("Rol asignado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al asignar rol: " + e.getMessage());
        }
    }

    // Eliminar un rol de un usuario
    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<String> deleteRole(
            @PathVariable("userId") Integer userId,
            @PathVariable("roleId") Integer roleId) {
        try {
            User user = userService.findById(userId);
            Role role = roleService.findById(roleId);

            if (user == null || role == null) {
                return ResponseEntity.badRequest().body("Usuario o rol no encontrado");
            }

            // Eliminar el rol del usuario
            userService.removeRole(userId, roleId);
            return ResponseEntity.ok("Rol eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar rol: " + e.getMessage());
        }
    }

    @GetMapping("/rolesPermissions")
    @ResponseBody // Indica que la respuesta será en formato JSON
    public ResponseEntity<Map<String, Object>> listRoles() {
        try {
            List<Role> roles = roleService.getAllRoles();
            List<Permission> permissions = permissionService.showAllPermissions();
            
            // Crear un mapa para combinar ambas listas
            Map<String, Object> response = new HashMap<>();
            response.put("roles", roles.stream().map(
                role -> {
                    RoleDTO dto = new RoleDTO();
                    dto.setId(role.getId());
                    dto.setName(role.getName());
                    dto.setPermissions(role.getPermissions().stream().map(
                        permission -> {
                            return permission.getId();
                        }
                    ).collect(Collectors.toList()));
                    return dto;
                }
            ).collect(Collectors.toList()));
            response.put("permissions", permissions.stream().map(
                permission -> {
                    PermissionDTO permissionDTO = new PermissionDTO();
                    permissionDTO.setId(permission.getId());
                    permissionDTO.setName(permission.getName());
                    permissionDTO.setDescription(permission.getDescription());
                    return permissionDTO;
                }
            ).collect(Collectors.toList()));

            return ResponseEntity.ok(response); // Respuesta exitosa con el mapa
        } catch (Exception e) {
            // En caso de error, devuelve un mensaje apropiado
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al obtener roles y permisos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/roles/create")
    public ResponseEntity<?> createRole(@RequestBody Map<String, String> payload) {
        try {
            String name = payload.get("name");
            String description = payload.get("description"); // Si es necesario
            Role role = new Role();
            role.setName(name);
            role.setDescription(description); // Asegúrate de que este campo exista en la entidad
            roleService.saveRole(role);
            return ResponseEntity.ok().body(Map.of("message", "Rol creado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al crear el rol: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/roles/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        try {
            Role role = roleService.findById(id);
            if (role != null) {
                roleService.delete(role);
                return ResponseEntity.ok().body(Map.of("message", "Rol eliminado exitosamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Rol no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al eliminar el rol: " + e.getMessage()));
        }
    }

    @PostMapping("/assign-permission")
    public ResponseEntity<?> assignPermissionToRole(@RequestBody Map<String, Integer> payload) {
        try {
            int roleId = payload.get("roleId");
            int permissionId = payload.get("permissionId");
            Role role = roleService.findById(roleId);
            Permission permission = permissionService.findById(permissionId);

            if (role == null || permission == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Rol o permiso no encontrado");
            }

            RolepermissionId rolePermissionId = new RolepermissionId();
            rolePermissionId.setRoleId(role.getId());
            rolePermissionId.setPermissionId(permission.getId());

            Rolepermission rolePermission = new Rolepermission();
            rolePermission.setId(rolePermissionId);
            rolePermission.setRole(role);
            rolePermission.setPermissions(permission);

            roleService.addPermission(rolePermission);

            return ResponseEntity.ok("Permiso asignado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al asignar permiso: " + e.getMessage());
        }
    }

    @GetMapping("/all-professors")
    public ResponseEntity<Map<String,Object>> getProfessors() {
        // Obtener los datos de usuarios y roles
        Map<String, Object> usersData = userService.getAllUsersWithRoles();
        List<User> users = (List<User>) usersData.get("users");
        Map<Integer, List<Role>> userRoles = (Map<Integer, List<Role>>) usersData.get("userRoles");

        // Filtrar usuarios que tienen el rol con ID 3
        Map<String, Object> response = new HashMap<>();
        List<User> professors = users.stream()
            .filter(user -> {
                List<Role> roles = userRoles.get(user.getId());
                return roles != null && roles.stream().anyMatch(role -> role.getId() == 2);
            })
            .collect(Collectors.toList());
        response.put("professors", professors.stream().map(
            professor -> {
                UserDTO dto = new UserDTO();
                dto.setId(professor.getId());
                dto.setName(professor.getName());
                dto.setUsername(professor.getUsername());
                dto.setBirthdate(professor.getBirthdate());
                return dto;
            }
        ).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-students")    
    public ResponseEntity<Map<String,Object>> getStudents() {
        // Obtener los datos de usuarios y roles
        Map<String, Object> usersData = userService.getAllUsersWithRoles();
        List<User> users = (List<User>) usersData.get("users");
        Map<Integer, List<Role>> userRoles = (Map<Integer, List<Role>>) usersData.get("userRoles");

        // Filtrar usuarios que tienen el rol con ID 2
        Map<String, Object> response = new HashMap<>();
        List<User> students = users.stream()
            .filter(user -> {
                List<Role> roles = userRoles.get(user.getId());
                return roles != null && roles.stream().anyMatch(role -> role.getId() == 3);
            })
            .collect(Collectors.toList());
        response.put("students", students.stream().map(
            student -> {
                UserDTO dto = new UserDTO();
                dto.setId(student.getId());
                dto.setName(student.getName());
                dto.setUsername(student.getUsername());
                dto.setBirthdate(student.getBirthdate());
                return dto;
            }
        ).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }
}
