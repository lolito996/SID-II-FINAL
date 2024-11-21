package com.example.demo.repositories.postgres;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.postgres.Subject;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	Subject findByName(String name);
}
