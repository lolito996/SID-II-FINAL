package com.example.demo.model.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RolepermissionId implements Serializable {
    private static final long serialVersionUID = -8361773536897746941L;
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Column(name = "permissions_id", nullable = false)
    private Integer permissionsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolepermissionId)) return false;
        RolepermissionId that = (RolepermissionId) o;
        return Objects.equals(roleId, that.roleId) && 
               Objects.equals(permissionsId, that.permissionsId);
    }

   @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionsId);
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionsId = permissionId;
    }

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}