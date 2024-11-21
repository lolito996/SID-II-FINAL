package com.example.demo.services.interfaces;

import java.util.List;
import java.time.LocalDate;
import java.util.Map;

import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.User;
import com.example.demo.model.postgres.Userrole;

public interface UserService {
    // Manage user
    public User findByUsername(String name);
    public void UpdateUser(String username, User user);
    public void updateUser(User updatedUser);
    public void deleteUser(String username);
    public List<User> getAllUsers();

    // Manage session (authentication)
    void registerUser(String username, String password, String name, String identificationNumber, LocalDate birthdate, String courseName);

    // Manage Students
    public void assignCourseStudent(String name, String courseName);

    // Manage User-Role
    public void assignRole(Userrole userRole);
    public void deleteRole(Userrole userRole);
    public List<User> findByRole(Role roleName);
    List<User> findAllUsers();
    public Map<String, Object> getAllUsersWithRoles();
    public User findById(Integer id);
    public List<Role> getRolesForUser(User user);
}