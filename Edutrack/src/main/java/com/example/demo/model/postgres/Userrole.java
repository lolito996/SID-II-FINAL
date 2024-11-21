  package com.example.demo.model.postgres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userrole")
public class Userrole {
    @EmbeddedId
    private UserroleId id;

    @MapsId("userCourseId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@MapsId("roleId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;



	public UserroleId getId() {
		return id;
	}

	public void setId(UserroleId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User userCourse) {
		this.user = userCourse;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}