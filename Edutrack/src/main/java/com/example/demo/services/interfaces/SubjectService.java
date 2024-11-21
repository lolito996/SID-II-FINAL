package com.example.demo.services.interfaces;

import java.util.List;

import com.example.demo.model.postgres.Subject;

public interface SubjectService {
	public List<Subject> showAllSubjects();
	public void save(Subject subject);
	public void delete(Subject subject);
	public Subject findByName(String name);
	public Subject findById(Integer id);
}
