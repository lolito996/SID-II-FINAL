package com.example.demo.controllers;

import com.example.demo.DTO.CourseDTO;
import com.example.demo.model.postgres.Course;
import com.example.demo.services.interfaces.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/course")
    @PreAuthorize("hasRole('Coordinador') or hasRole('Profesor') or hasRole('Asistente') or hasRole ('COURSE_ENROLLABLE') or hasRole('Administrador')")
    public ResponseEntity<String> saveCourse(@RequestBody CourseDTO courseDTO) {
        try {
            Course course = new Course();
            course.setName(courseDTO.getName());
            course.setCapacity(courseDTO.getCapacity());

            courseService.saveCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating course: " + e.getMessage());
        }
    }

    @GetMapping("/course/{name}")
    @PreAuthorize("hasRole('Coordinador') or hasRole('Profesor') or hasRole('Asistente') or hasRole ('COURSE_ENROLLABLE') or hasRole('Administrador')")
    public ResponseEntity<CourseDTO> getCourseByName(@PathVariable("name") String name) {
        try {
            Course course = courseService.findByName(name);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            courseDTO.setCapacity(course.getCapacity());

            return ResponseEntity.ok(courseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
