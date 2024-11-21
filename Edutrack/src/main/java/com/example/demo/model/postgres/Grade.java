package com.example.demo.model.postgres;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false, length = Integer.MAX_VALUE)
    private Integer id;

    @Column(name = "final_grade", nullable = false)
    private BigDecimal finalGrade;

    @Column(name = "observations", length = Integer.MAX_VALUE)
    private String observations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_course_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subjectGrade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(BigDecimal finalGrade) {
		this.finalGrade = finalGrade;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User userCourse) {
		this.user = userCourse;
	}

}