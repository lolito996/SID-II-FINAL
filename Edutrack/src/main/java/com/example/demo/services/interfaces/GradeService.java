package com.example.demo.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.model.postgres.Grade;
import com.example.demo.model.mongo.GradeDocument;
public interface GradeService {
	void assignGrade(Grade grade);
	Grade findGradeStudentSubject(Integer studentId, Integer subjectId);
	List<Grade> findAllGradeStudent(Integer studentId);
	void updateGradeStudentSubject(Integer studentId, Integer subjectId, BigDecimal newGrade, String observations);
	void deleteGradePostgres(Integer studentId, Integer subjectId);


	//mongo
	void assignGradeMongo(com.example.demo.model.mongo.GradeDocument gradeDocument);
	GradeDocument findGradeStudentSubjectMongo(Integer studentId, Integer subjectId);
	List<GradeDocument> findAllGradeStudentMongo(Integer studentId);
	void updateGradeStudentSubjectMongo(Integer studentId, Integer subjectId, BigDecimal newGrade, String observations);
	void createSubgradeMongo(Integer studentId, Integer subjectId, String subGradeName, BigDecimal newGrade, BigDecimal subGradeValue);
	void updateSubgradeMongo(Integer studentId, Integer subjectId, String subGradeName, BigDecimal subGradeValue);
	BigDecimal calculateFinalGrade(List<GradeDocument.SubGrade> subGrades);
	void deleteGradeMongo(Integer studentId, Integer subjectId);
    void deleteSubgradeMongo(Integer studentId, Integer subjectId, String subGradeName);

	void validateWeights(List<GradeDocument.SubGrade> subGrades);
	
}
