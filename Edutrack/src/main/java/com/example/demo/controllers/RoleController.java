package com.example.demo.controllers;

import com.example.demo.model.postgres.Permission;
import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.Rolepermission;
import com.example.demo.model.postgres.RolepermissionId;
import com.example.demo.services.interfaces.PermissionService;
import com.example.demo.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    // Crear un nuevo rol
    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGE_USERS')")
    public String createRole(@RequestParam String name, RedirectAttributes redirectAttributes) {
        try {
            Role role = new Role();
            role.setName(name);
            roleService.saveRole(role);
            redirectAttributes.addFlashAttribute("success", "Rol creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el rol: " + e.getMessage());
        }
        return "redirect:/admin/roles";
    }

    // Eliminar un rol
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('MANAGE_USERS')")
    public String deleteRole(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Role role = roleService.findById(id);
            if (role != null) {
                roleService.delete(role);
                redirectAttributes.addFlashAttribute("success", "Rol eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", "Rol no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el rol: " + e.getMessage());
        }
        return "redirect:/admin/roles";
    }

    // Asignar permiso a un rol
    @PostMapping("/assign-permission")
    @PreAuthorize("hasRole('MANAGE_USERS')")
    public String assignPermissionToRole(
            @RequestParam("roleId") Integer roleId,
            @RequestParam("permissionId") Integer permissionId,
            RedirectAttributes redirectAttributes) {
        try {
            Role role = roleService.findById(roleId);
            Permission permission = permissionService.findById(permissionId);

            if (role == null || permission == null) {
                redirectAttributes.addFlashAttribute("error", "Rol o permiso no encontrado");
                return "redirect:/admin/roles";
            }

            RolepermissionId rolePermissionId = new RolepermissionId();
            rolePermissionId.setRoleId(role.getId());
            rolePermissionId.setPermissionId(permission.getId());

            Rolepermission rolePermission = new Rolepermission();
            rolePermission.setId(rolePermissionId);
            rolePermission.setRole(role);
            rolePermission.setPermissions(permission);

            roleService.addPermission(rolePermission);
            redirectAttributes.addFlashAttribute("success", "Permiso asignado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al asignar permiso: " + e.getMessage());
        }
        return "redirect:/admin/roles";
    }

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}