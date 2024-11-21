package com.example.demo.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.postgres.Permission;


public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	Permission findByName(String name);
	
	@Query(value="Select pe.* from permissions pe, rolepermissions  rp, userrole ur "
			+ "where pe.permissions_id = rp.permissions_id "
			+ "and ur.role_id = rp.role_id "
			+ "and ur.user_id = ?1",
	        nativeQuery = true)
	List<Permission> findByUser(Integer user);	
}
