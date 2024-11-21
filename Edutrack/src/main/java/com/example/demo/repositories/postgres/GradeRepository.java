package com.example.demo.repositories.postgres;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.postgres.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
	
	@Query(value = "Select final_grade from grades where "
			+ "user_course_id = ?1 and subject_id = ?2 ",
			nativeQuery = true)
	Grade findGradeByStudentSubject(Integer studentId, Integer subjectId);
	
	@Query(value = "Select * from grades where "
			+ "user_course_id = ?1",
			nativeQuery = true)
	List<Grade> findAllGradeByStudent(Integer studentId);
	
	@Query(value = "Select * from grades where "
			+ "user_course_id = ?1 and subject_id = ?2 ",
			nativeQuery = true)
	Grade getGradeStudenSubject(Integer studentId, Integer subjectId);
}