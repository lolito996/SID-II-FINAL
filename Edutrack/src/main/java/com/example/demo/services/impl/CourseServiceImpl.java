package com.example.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exeptions.IllegalArgumentException;
import com.example.demo.model.postgres.Course;
import com.example.demo.repositories.postgres.CourseRepository;
import com.example.demo.services.interfaces.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void saveCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        courseRepository.save(course);
    }

    @Override
    public Course findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }

        Course course = courseRepository.findByName(name);
        if (course == null) {
            throw new RuntimeException("Course with name " + name + " not found");
        }
        return course;
    }
}
        

