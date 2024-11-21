package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.demo.services.interfaces.UserService;
import com.example.demo.services.interfaces.RoleService;
import com.example.demo.model.postgres.Permission;
import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.User;
import com.example.demo.services.interfaces.PermissionService;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('MANAGE_USERS')")
    public String listUsers(Model model) {
        Map<String, Object> usersData = userService.getAllUsersWithRoles();
        List<User> users = (List<User>) usersData.get("users");
        Map<Integer, List<Role>> userRoles = (Map<Integer, List<Role>>) usersData.get("userRoles");
        List<Role> allRoles = roleService.getAllRoles();

        model.addAttribute("users", users);
        model.addAttribute("userRoles", userRoles);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("title", "Gestión de Usuarios");
        return "admin/user_management";
    }


    // Página para la gestión de roles y permisos
    @GetMapping("/admin/roles")
    @PreAuthorize("hasRole('MANAGE_USERS') or hasRole('MANAGE_ROLES')")
    public String listRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        List<Permission> permissions = permissionService.showAllPermissions();
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("title", "Gestión de Roles");
        return "admin/role_management";
    }
}
