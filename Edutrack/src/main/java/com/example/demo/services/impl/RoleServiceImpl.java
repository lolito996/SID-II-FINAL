package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.postgres.Permission;
import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.Rolepermission;
import com.example.demo.model.postgres.User;
import com.example.demo.repositories.postgres.PermissionRepository;
import com.example.demo.repositories.postgres.RolePermissionRepository;
import com.example.demo.repositories.postgres.RoleRepository;
import com.example.demo.services.interfaces.RoleService;

import jakarta.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository rolRepo;

    @Autowired
    private RolePermissionRepository rolPermissionRepo;
    
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Role> getAllRoles() {
        return rolRepo.findAll();
    }

    @Override
    public Role findByName(String name) {
        return rolRepo.findByName(name);
    }

    @Override
    public void saveRole(Role rol) {
        rolRepo.save(rol);
    }

    @Override
    public void delete(Role rol) {
        rolRepo.delete(rol);
    }

    @Override
    @Transactional
    public void addPermission(Rolepermission rolPer) {
        if (rolPer.getRole() == null || rolPer.getPermissions() == null) {
            throw new IllegalArgumentException("Role and Permission must be set");
        }
        rolPermissionRepo.save(rolPer);
    }
    


    @Override
    public List<Rolepermission> searchRolebyPermission(Permission permission) {
        return rolPermissionRepo.findByPermission(permission);
    }

    @Override
    public List<Rolepermission> findByRole(Role roleRole) {
        return rolPermissionRepo.findByRole(roleRole);
    }

	@Override
	public List<Permission> getUserPermissions(User user) {
		return permissionRepository.findByUser(user.getId());
	}

    @Override
    public Role findById(Integer id) {
        return rolRepo.findById(id)
                .orElse(null); // Devolver null si no se encuentra el rol
    }
}