package com.example.demo.model.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Integer id;

	@Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Rolepermission> rolepermissions;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Rolepermission> getRolepermissions() {
		return rolepermissions;
	}

	public void setRolepermissions(List<Rolepermission> rolepermissions) {
		this.rolepermissions = rolepermissions;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Permission> getPermissions() {
        return rolepermissions.stream()
                              .map(Rolepermission::getPermissions)
                              .collect(Collectors.toList());
    }
	

    public Integer getId() {
		return id;
	}
}