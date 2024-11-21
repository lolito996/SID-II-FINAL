package com.example.demo.DTO;
import java.math.BigDecimal;

public class GradeDTO {
	
	private String student;
	private String observations;
	private BigDecimal grade;
	private String subject;
	private boolean pass;
	
	
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public BigDecimal getGrade() {
		return grade;
	}
	public void setGrade(BigDecimal grade) {
		this.grade = grade;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	
	

}