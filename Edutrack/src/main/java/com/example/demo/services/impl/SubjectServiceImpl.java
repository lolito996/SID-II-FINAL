package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.postgres.Subject;
import com.example.demo.repositories.postgres.SubjectRepository;
import com.example.demo.services.interfaces.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired
	private SubjectRepository subjectRepo;
	
	@Override
	public List<Subject> showAllSubjects() {
		return subjectRepo.findAll();
	}

	@Override
	public Subject findByName(String name) {
		return subjectRepo.findByName(name);
	}

	@Override
	public Subject findById(Integer id) {
		return subjectRepo.findById(id).orElse(null);
	}
	
	@Override
	public void save(Subject subject) {
		subjectRepo.save(subject);
	}

	@Override
	public void delete(Subject subject) {
		subjectRepo.delete(subject);
	}


}