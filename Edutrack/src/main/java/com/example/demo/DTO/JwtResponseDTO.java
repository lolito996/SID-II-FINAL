package com.example.demo.DTO;

import java.util.List;

public class JwtResponseDTO {
    private String token;
    private Integer id;
    private String username;
    private Integer courseId; 
    private List<String> roles;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public JwtResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    // Constructor
    public JwtResponseDTO(String token, Integer id, String username, Integer courseId, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.courseId = courseId;
        this.roles = roles;
    }
    
}
