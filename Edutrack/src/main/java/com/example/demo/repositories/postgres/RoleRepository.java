package com.example.demo.repositories.postgres;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.postgres.Permission;
import com.example.demo.model.postgres.Role; 

public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByName(String name);

	    
	
}
