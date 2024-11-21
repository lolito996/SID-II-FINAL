package com.example.demo.services.interfaces;

import com.example.demo.model.postgres.Course;

public interface CourseService {
	public void saveCourse(Course course); 
	public Course findByName(String name);
}
