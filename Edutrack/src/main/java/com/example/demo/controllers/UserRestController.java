package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.services.interfaces.UserService;
import com.example.demo.services.interfaces.RoleService;
import com.example.demo.DTO.UserDTO;
import com.example.demo.model.postgres.User;
import com.example.demo.model.postgres.Role;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // Ruta para obtener usuarios filtrados por roleId
    @GetMapping("/role/{roleId}")
    public List<UserDTO> getUsersByRole(@PathVariable("roleId") Integer roleId) {
        // Obtener los datos de usuarios y roles
        Map<String, Object> usersData = userService.getAllUsersWithRoles();
        List<User> users = (List<User>) usersData.get("users");
        Map<Integer, List<Role>> userRoles = (Map<Integer, List<Role>>) usersData.get("userRoles");

        // Filtrar usuarios que tienen el rol con el roleId dado
        return users.stream()
                .filter(user -> {
                    List<Role> roles = userRoles.get(user.getId());
                    return roles != null && roles.stream().anyMatch(role -> role.getId().equals(roleId));
                })
                .map(user -> {
                    // Mapear a DTO
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }


}
