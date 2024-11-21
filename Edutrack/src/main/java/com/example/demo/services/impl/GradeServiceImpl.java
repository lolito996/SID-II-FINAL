package com.example.demo.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.mongo.GradeDocument;
import com.example.demo.model.postgres.Grade;
import com.example.demo.repositories.postgres.GradeRepository;
import com.example.demo.repositories.mongo.GradeDocumentRepository;
import com.example.demo.services.interfaces.GradeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GradeServiceImpl implements GradeService{

	private static final Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);
	
	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private GradeDocumentRepository gradeDocumentRepository;

	@Override
	public void assignGrade(Grade grade) {
		gradeRepository.save(grade);
	}

	//mongo
	@Override
	public void assignGradeMongo(GradeDocument gradeDocument) {
		gradeDocumentRepository.save(gradeDocument);
	}

	@Override
	public Grade findGradeStudentSubject(Integer studentId, Integer subjectId) {
		return gradeRepository.findGradeByStudentSubject(studentId, subjectId);
	}

	@Override
	public GradeDocument findGradeStudentSubjectMongo(Integer studentId, Integer subjectId) {
		GradeDocument grade = gradeDocumentRepository.findGradeByStudentSubject(studentId, subjectId);
		if (grade == null) {
			System.out.println("No se encontró la calificación en MongoDB para studentId: " + studentId + " y subjectId: " + subjectId);
		}
		return grade;
	}

	@Override
	public List<Grade> findAllGradeStudent(Integer studentId) {
		return gradeRepository.findAllGradeByStudent(studentId);
	}

	@Override
	public List<GradeDocument> findAllGradeStudentMongo(Integer studentId) {
		return gradeDocumentRepository.findByUserId(studentId);
	}

	@Override
	public void updateGradeStudentSubject(Integer studentId, Integer subjectId, BigDecimal newGrade, String observation) {
		Grade gradeStudent= gradeRepository.getGradeStudenSubject(studentId, subjectId);
		gradeStudent.setFinalGrade(newGrade);
		gradeStudent.setObservations(observation);
		assignGrade(gradeStudent);
	}

	@Override
	public void updateGradeStudentSubjectMongo(Integer studentId, Integer subjectId, BigDecimal newGrade, String observation) {
		GradeDocument gradeStudent = gradeDocumentRepository.findGradeByStudentSubject(studentId, subjectId);
		if (gradeStudent == null) {
			throw new IllegalArgumentException("No se encontró la calificación en MongoDB para studentId: " + studentId + " y subjectId: " + subjectId);
		}
		gradeStudent.setFinalGrade(newGrade);
		gradeStudent.setObservations(List.of(new GradeDocument.Observation(observation, LocalDate.now())));
		assignGradeMongo(gradeStudent);
	}

	@Override
	public void createSubgradeMongo(Integer studentId, Integer subjectId, String subGradeName, BigDecimal newGrade, BigDecimal weight) {
		GradeDocument gradeDocument = gradeDocumentRepository.findGradeByStudentSubject(studentId, subjectId);

		if (gradeDocument == null) {
			// Crear la estructura inicial si no existe
			gradeDocument = new GradeDocument();
			gradeDocument.setStudentId(studentId);
			gradeDocument.setSubjectId(subjectId);
			gradeDocument.setSubGrades(new ArrayList<>());
		}

		// Verificar si la subnota ya existe
		boolean subGradeExists = gradeDocument.getSubGrades().stream()
				.anyMatch(subGrade -> subGrade.getName().equals(subGradeName));

		if (subGradeExists) {
			throw new IllegalArgumentException("La subnota ya existe para el nombre especificado.");
		} 

		// Añadir la nueva subnota
		gradeDocument.addSubGrade(subGradeName, newGrade, weight);

		// Recalcular la nota final
		BigDecimal finalGrade = calculateFinalGrade(gradeDocument.getSubGrades());
		gradeDocument.setFinalGrade(finalGrade);

		gradeDocumentRepository.save(gradeDocument);

	}

	@Override
	public void updateSubgradeMongo(Integer studentId, Integer subjectId, String subGradeName, BigDecimal newGrade) {
		GradeDocument gradeDocument = gradeDocumentRepository.findGradeByStudentSubject(studentId, subjectId);

		if (gradeDocument == null) {
			throw new IllegalArgumentException("No se encontró la calificación para el estudiante y materia especificados.");
		}

		// Actualizar la subnota
		gradeDocument.getSubGrades().stream()
				.filter(subGrade -> subGrade.getName().equals(subGradeName))
				.findFirst()
				.ifPresent(subGrade -> subGrade.setGrade(newGrade));

		// Recalcular la nota final
		gradeDocument.setFinalGrade(calculateFinalGrade(gradeDocument.getSubGrades()));

		gradeDocumentRepository.save(gradeDocument);
	}

	@Override
	public void validateWeights(List<GradeDocument.SubGrade> subGrades) {
		BigDecimal totalWeight = subGrades.stream()
				.map(GradeDocument.SubGrade::getWeight)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (totalWeight.compareTo(BigDecimal.ONE) > 0) {
			throw new IllegalArgumentException("Los pesos de las subnotas exceden el 100%.");
		}
	}


	@Override
	public BigDecimal calculateFinalGrade(List<GradeDocument.SubGrade> subGrades) {
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal weightedSum = BigDecimal.ZERO;

        for (GradeDocument.SubGrade subGrade : subGrades) {
            totalWeight = totalWeight.add(subGrade.getWeight());
            weightedSum = weightedSum.add(subGrade.getGrade().multiply(subGrade.getWeight()));
        }

        if (totalWeight.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Evitar división por cero
        }

        return weightedSum.divide(totalWeight, 2, BigDecimal.ROUND_HALF_UP); // Promedio ponderado
    }

	@Override
	public void deleteSubgradeMongo(Integer studentId, Integer subjectId, String subGradeName) {
		GradeDocument gradeDocument = gradeDocumentRepository.findGradeByStudentSubject(studentId, subjectId);

		if (gradeDocument == null) {
			throw new IllegalArgumentException("No se encontró la calificación para el estudiante y materia especificados.");
		}

		// Filtrar la subnota a eliminar
		List<GradeDocument.SubGrade> updatedSubGrades = gradeDocument.getSubGrades().stream()
				.filter(subGrade -> !subGrade.getName().equals(subGradeName))
				.collect(Collectors.toList());

		// Actualizar el documento con las subnotas restantes
		gradeDocument.setSubGrades(updatedSubGrades);
		gradeDocument.setFinalGrade(calculateFinalGrade(updatedSubGrades));

		gradeDocumentRepository.save(gradeDocument);
	}

	@Override
	public void deleteGradePostgres(Integer studentId, Integer subjectId) {
		Grade grade = gradeRepository.getGradeStudenSubject(studentId, subjectId);
		gradeRepository.delete(grade);
	}

	@Override
	public void deleteGradeMongo(Integer studentId, Integer subjectId) {
		GradeDocument gradeDocument = gradeDocumentRepository.findGradeByStudentSubject(studentId, subjectId);
		gradeDocumentRepository.delete(gradeDocument);
	}
}