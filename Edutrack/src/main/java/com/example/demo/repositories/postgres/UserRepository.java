package com.example.demo.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.postgres.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Devuelve un Optional para manejo seguro de nulos
    User findByUsername(String username);
    @Transactional
    void deleteById(@SuppressWarnings("null") Integer id);
}
