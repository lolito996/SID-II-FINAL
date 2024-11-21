package com.example.demo.controllers;

import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.User;
import com.example.demo.model.postgres.Userrole;
import com.example.demo.model.postgres.UserroleId;
import com.example.demo.services.impl.UserServiceImpl;
import com.example.demo.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class UserRoleController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleService roleService;

    // Asignar un rol a un usuario
    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('MANAGE_USERS') or hasRole('MANAGE_ROLES')")
    public String assignRole(
            @RequestParam("userId") Integer userId, 
            @RequestParam("roleId") Integer roleId, 
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findById(userId);  
            Role role = roleService.findById(roleId);  

            if (user == null || role == null) {
                redirectAttributes.addFlashAttribute("error", "Usuario o rol no encontrado");
                return "redirect:/admin/users";
            }

            // Crear y asignar el rol
            Userrole userRole = new Userrole();
            UserroleId userRoleId = new UserroleId();
            userRoleId.setUserCourseId(user.getId());  // Método correcto para asignar userId
            userRoleId.setRoleId(role.getId());
            userRole.setId(userRoleId);
            userRole.setUser(user);
            userRole.setRole(role);

            userService.assignRole(userRole);
            redirectAttributes.addFlashAttribute("success", "Rol asignado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al asignar rol: " + e.getMessage());
        }
        return "redirect:/admin/users"; // Redirigir de vuelta a la página de gestión de usuarios
    }

    // Eliminar un rol de un usuario
    @PostMapping("/remove-role/{userId}/{roleId}")
    @PreAuthorize("hasRole('MANAGE_USERS') or hasRole('MANAGE_ROLES')")
    public String deleteRole(
            @PathVariable("userId") Integer userId,  // Cambiado a Integer
            @PathVariable("roleId") Integer roleId,  // Cambiado a Integer
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findById(userId);  // userId ahora es Integer
            Role role = roleService.findById(roleId);  // roleId ahora es Integer

            if (user == null || role == null) {
                redirectAttributes.addFlashAttribute("error", "Usuario o rol no encontrado");
                return "redirect:/admin/users";
            }

            // Eliminar el rol del usuario
            userService.removeRole(userId, roleId);  // Este método debe manejar Integer
            redirectAttributes.addFlashAttribute("success", "Rol eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar rol: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}