package com.example.demo.DTO;


public class ScheduleDTO {
	
    private String startTime;

    private String endTime;

    private String classroom;

    private String days;

    private String course;

    private String subject;

    private String userCourse;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserCourse() {
		return userCourse;
	}

	public void setUserCourse(String userCourse) {
		this.userCourse = userCourse;
	}


}