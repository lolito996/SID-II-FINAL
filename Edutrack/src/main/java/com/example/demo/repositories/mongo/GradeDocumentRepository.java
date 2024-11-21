package com.example.demo.repositories.mongo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.mongo.GradeDocument;

@Repository
public interface GradeDocumentRepository extends MongoRepository<GradeDocument, String> {

    // Encuentra una calificación por estudiante y materia
    @Query("{ 'studentId' : ?0, 'subjectId' : ?1 }")
    GradeDocument findGradeByStudentSubject(Integer studentId, Integer subjectId);

    // Encuentra todas las calificaciones de un estudiante
    @Query("{ 'studentId' : ?0 }")
    List<GradeDocument> findAllGradeByStudent(Integer studentId);

    // Encuentra una subnota específica por estudiante, materia y nombre de la subnota
    @Query("{ 'studentId' : ?0, 'subjectId' : ?1, 'subGrades.name' : ?2 }")
    GradeDocument findGradeWithSubGradeByName(Integer studentId, Integer subjectId, String subGradeName);

    // Encuentra todas las subnotas de un estudiante para una materia
    @Query("{ 'studentId' : ?0, 'subjectId' : ?1 }")
    List<GradeDocument.SubGrade> findAllSubGradesByStudentAndSubject(Integer studentId, Integer subjectId);
    
    // Encuentra una calificación específica por ID
    @Query("{ '_id' : ?0 }")
    GradeDocument findGradeById(String id);

    // Encuentra todas las calificaciones de un estudiante por ID
    @Query("{ 'studentId' : ?0 }")
    List<GradeDocument> findByUserId(Integer studentId);
}
