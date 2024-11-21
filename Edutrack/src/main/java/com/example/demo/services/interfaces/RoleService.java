package com.example.demo.services.interfaces;

import com.example.demo.model.postgres.Permission;
import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.Rolepermission;
import com.example.demo.model.postgres.User;

import java.util.List;

public interface RoleService {
	public List<Role> getAllRoles();
	public Role findByName(String name);
	public void saveRole(Role rol);
	public void delete(Role rol);
	
	//managa permission
	public void  addPermission(Rolepermission rolPer);
	public List<Rolepermission>  searchRolebyPermission(Permission permission);
	public List<Rolepermission>  findByRole(Role role);
	public List<Permission> getUserPermissions(User user);
	Role findById(Integer id);
}
