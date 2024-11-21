package com.example.demo.model.postgres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rolepermissions")
public class Rolepermission {
    @EmbeddedId
    private RolepermissionId id;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @MapsId("permissionsId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permissions_id", nullable = false)
    private Permission permission;


	public RolepermissionId getId() {
		return id;
	}

	public void setId(RolepermissionId id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermissions() {
		return permission;
	}

	public void setPermissions(Permission permission) {
		this.permission = permission;
	}

}