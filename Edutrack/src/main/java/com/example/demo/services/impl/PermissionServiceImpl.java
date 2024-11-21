package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.postgres.Permission;
import com.example.demo.repositories.postgres.PermissionRepository;
import com.example.demo.services.interfaces.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepo;
	
	@Override
	public List<Permission> showAllPermissions() {
		return permissionRepo.findAll();
	}

	@Override
	public void savePermission(Permission permission) {
		permissionRepo.save(permission);
	}

	@Override
	public void deletePermission(Permission permission) {
		permissionRepo.delete(permission);
	}


	@Override
	public Permission findByName(String name) {
		return permissionRepo.findByName(name);  // Asegúrate de que este método esté definido en tu repositorio
	}

	@Override
    public Permission findById(Integer id) {
        return permissionRepo.findById(id).orElse(null);
    }

}
