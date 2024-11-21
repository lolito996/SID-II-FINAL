package com.example.demo.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.postgres.Permission;
import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.Rolepermission;
import com.example.demo.model.postgres.RolepermissionId;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<Rolepermission, RolepermissionId> {
    List<Rolepermission> findByPermission(Permission permission); 
    List<Rolepermission> findByRole(Role role);
}
