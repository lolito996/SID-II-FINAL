package com.example.demo.repositories.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.postgres.Course;


@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	Course findByName(String name);
}
