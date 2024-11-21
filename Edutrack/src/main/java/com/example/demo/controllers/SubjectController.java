package com.example.demo.controllers;

import com.example.demo.DTO.SubjectDTO;
import com.example.demo.model.postgres.Subject;
import com.example.demo.services.interfaces.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    @PreAuthorize("hasRole('ENROLL_STUDENTS') or hasRole('ASSIGN_TEACHERS_TO_CLASSES')")
    public ResponseEntity<List<SubjectDTO>> showSubjects() {
        try {
            List<SubjectDTO> subjects = subjectService.showAllSubjects().stream()
                    .map(subject -> {
                        SubjectDTO dto = new SubjectDTO();
                        dto.setId(subject.getId());
                        dto.setName(subject.getName());
                        dto.setDescription(subject.getDescription());
                        dto.setMinimumPassingGrade(subject.getMinimumPassingGrade());
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ENROLL_STUDENTS') or hasRole('ASSIGN_TEACHERS_TO_CLASSES')")
    public ResponseEntity<SubjectDTO> showOneSubject(@PathVariable("name") String name) {
        try {
            Subject subject = subjectService.findByName(name);
            if (subject == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            SubjectDTO dto = new SubjectDTO();
            dto.setId(subject.getId());
            dto.setName(subject.getName());
            dto.setDescription(subject.getDescription());
            dto.setMinimumPassingGrade(subject.getMinimumPassingGrade());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ASSIGN_TEACHERS_TO_CLASSES')")
    public ResponseEntity<String> saveSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            Subject subject = new Subject();
            subject.setId(subjectDTO.getId());
            subject.setName(subjectDTO.getName());
            subject.setDescription(subjectDTO.getDescription());
            subject.setMinimumPassingGrade(subjectDTO.getMinimumPassingGrade());

            subjectService.save(subject);
            return ResponseEntity.status(HttpStatus.CREATED).body("Subject created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating subject: " + e.getMessage());
        }
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('ASSIGN_TEACHERS_TO_CLASSES')")
    public ResponseEntity<String> deleteSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            Subject subject = new Subject();
            subject.setId(subjectDTO.getId());
            subject.setName(subjectDTO.getName());
            subject.setDescription(subjectDTO.getDescription());
            subject.setMinimumPassingGrade(subjectDTO.getMinimumPassingGrade());

            subjectService.delete(subject);
            return ResponseEntity.ok("Subject deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting subject: " + e.getMessage());
        }
    }
}
