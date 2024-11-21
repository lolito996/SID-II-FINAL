package com.example.demo.services.interfaces;

import java.util.List;

import com.example.demo.model.postgres.Permission;

public interface PermissionService {

	public List<Permission> showAllPermissions();
	public void savePermission(Permission permission); 
	public void deletePermission(Permission permission);
	public Permission findByName(String name);  
	Permission findById(Integer id);

}
