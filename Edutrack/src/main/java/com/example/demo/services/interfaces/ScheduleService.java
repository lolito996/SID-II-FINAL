package com.example.demo.services.interfaces;
import java.util.List;

import com.example.demo.model.postgres.Schedule;


public interface ScheduleService {
	void createSchedule(Schedule newSchedule);
	List<Schedule> getScheduleByTeacher(Integer teacherID);
	List<Schedule> getScheduleByCourse(Integer courseID);
	Schedule getScheduleByID(Integer scheduleID);
	void setTeacherFromSchedule(Integer scheduleID, Integer newteacherID);
}
