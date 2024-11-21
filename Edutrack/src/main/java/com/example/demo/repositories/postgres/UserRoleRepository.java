package com.example.demo.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.User;
import com.example.demo.model.postgres.Userrole;
import com.example.demo.model.postgres.UserroleId;

import java.util.List;




public interface UserRoleRepository extends JpaRepository<Userrole, UserroleId> {
	
	List<Userrole> findByRole(Role role);
	List<Userrole> findByUser(User user);
	void deleteByUserIdAndRoleId(Integer userId, Integer roleId); 
}
