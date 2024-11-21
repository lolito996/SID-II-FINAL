package com.example.demo.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.GradeDTO;
import com.example.demo.model.mongo.GradeDocument;
import com.example.demo.model.postgres.Grade;
import com.example.demo.model.postgres.Subject;
import com.example.demo.model.postgres.User;
import com.example.demo.services.impl.GradeServiceImpl;
import com.example.demo.services.interfaces.SubjectService;
import com.example.demo.services.interfaces.UserService;

@RestController
@RequestMapping("/api/grade")
public class GradesController{
	
	@Autowired
	GradeServiceImpl gradeService;
	@Autowired
    UserService userService;
	@Autowired
	SubjectService subjectService;
    
	public GradesController(GradeServiceImpl gradeService, UserService userService, SubjectService subjectService) {
		this.gradeService = gradeService;
		this.userService = userService;
		this.subjectService = subjectService;
	}
	
	@GetMapping("student/subject")
    @PreAuthorize("hasRole('GRADE_ASSIGNMENT') or #idS == authentication.principal.id")
    public ResponseEntity<?> gradeStudentOneSubject(
            @RequestParam("idS") Integer idS,
            @RequestParam("idSubject") Integer idSubject,
            @RequestParam(value = "useMongo", required = false, defaultValue = "true") boolean useMongo) {

        if (useMongo) {
            GradeDocument gradeDocument = gradeService.findGradeStudentSubjectMongo(idS, idSubject);
            if (gradeDocument != null) {
                return ResponseEntity.ok(gradeDocument);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Calificación no encontrada en MongoDB");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body("El manejo de subnotas aún no está implementado para PostgreSQL.");
        }
    }



	@GetMapping("student/{id}")
    @PreAuthorize("hasRole('GRADE_ASSIGNMENT') or #id == authentication.principal.id")
    public ResponseEntity<?> allGradesStudent(
            @PathVariable("id") Integer id,
            @RequestParam(value = "useMongo", required = false, defaultValue = "true") boolean useMongo) {

        if (useMongo) {
            List<GradeDocument> mongoGrades = gradeService.findAllGradeStudentMongo(id);
            return ResponseEntity.ok(mongoGrades);
        } else {
            List<Grade> postgresGrades = gradeService.findAllGradeStudent(id);
            List<GradeDTO> dtoList = postgresGrades.stream()
                    .map(grade -> {
                        GradeDTO dto = new GradeDTO();
                        dto.setStudent(grade.getUser().getName());
                        dto.setObservations(grade.getObservations());
                        dto.setGrade(grade.getFinalGrade());
                        dto.setSubject(grade.getSubjectGrade().getName());
                        dto.setPass(grade.getFinalGrade()
                                .compareTo(grade.getSubjectGrade().getMinimumPassingGrade()) >= 0);
                        return dto;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        }
    }


    

    @PostMapping("/subgrade/create")
    @PreAuthorize("hasRole('GRADE_ASSIGNMENT')")
    public ResponseEntity<String> createSubgrade(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("subjectId") Integer subjectId,
            @RequestParam("subGradeName") String subGradeName,
            @RequestParam("grade") BigDecimal grade,
            @RequestParam("weight") BigDecimal weight,
            @RequestParam(value = "useMongo", required = false, defaultValue = "true") boolean useMongo) {

        try {
            if (useMongo) {
                gradeService.createSubgradeMongo(studentId, subjectId, subGradeName, grade, weight);
                return ResponseEntity.status(HttpStatus.CREATED).body("Subnota creada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body("El manejo de subnotas aún no está implementado para PostgreSQL.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la subnota: " + e.getMessage());
        }
    }

    @PutMapping("/subgrade/update")
    @PreAuthorize("hasRole('GRADE_ASSIGNMENT')")
    public ResponseEntity<String> updateSubgrade(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("subjectId") Integer subjectId,
            @RequestParam("subGradeName") String subGradeName,
            @RequestParam("grade") BigDecimal grade,
            @RequestParam("weight") BigDecimal weight,
            @RequestParam(value = "useMongo", required = false, defaultValue = "true") boolean useMongo) {

        try {
            if (useMongo) {
                gradeService.updateSubgradeMongo(studentId, subjectId, subGradeName, weight);
                return ResponseEntity.ok("Subnota actualizada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body("El manejo de subnotas aún no está implementado para PostgreSQL.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la subnota: " + e.getMessage());
        }
    }

    @GetMapping("/subgrades")
    @PreAuthorize("hasRole('GRADE_ASSIGNMENT') or #studentId == authentication.principal.id")
    public ResponseEntity<?> listSubgrades(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("subjectId") Integer subjectId,
            @RequestParam(value = "useMongo", required = false, defaultValue = "true") boolean useMongo) {

        try {
            if (useMongo) {
                GradeDocument gradeDocument = gradeService.findGradeStudentSubjectMongo(studentId, subjectId);
                if (gradeDocument != null) {
                    return ResponseEntity.ok(gradeDocument.getSubGrades());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron subnotas.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body("El manejo de subnotas aún no está implementado para PostgreSQL.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar subnotas: " + e.getMessage());
        }
    }


    @DeleteMapping("/subgrade")
    @PreAuthorize("hasRole('GRADE_ASSIGNMENT')")
    public ResponseEntity<String> deleteSubgrade(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("subjectId") Integer subjectId,
            @RequestParam("subGradeName") String subGradeName,
            @RequestParam(value = "useMongo", required = false, defaultValue = "true") boolean useMongo) {

        try {
            if (useMongo) {
                gradeService.deleteSubgradeMongo(studentId, subjectId, subGradeName);
                return ResponseEntity.ok("Subnota eliminada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body("El manejo de subnotas aún no está implementado para PostgreSQL.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la subnota: " + e.getMessage());
        }
    }

}
	 
	
	 
	 
