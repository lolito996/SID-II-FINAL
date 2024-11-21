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
public class UserroleId implements Serializable {
    private static final long serialVersionUID = 3095484627834070549L;
    
    @Column(name = "user_id", nullable = false, length = 10)
    private Integer userCourseId;
    
	@Column(name = "role_id", nullable = false)
    private Integer roleId;
	
    public Integer getUserCourseId() {
		return userCourseId;
	}

	public void setUserCourseId(Integer userCourseId) {
		this.userCourseId = userCourseId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserroleId entity = (UserroleId) o;
        return Objects.equals(this.userCourseId, entity.userCourseId) &&
                Objects.equals(this.roleId, entity.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCourseId, roleId);
    }

}