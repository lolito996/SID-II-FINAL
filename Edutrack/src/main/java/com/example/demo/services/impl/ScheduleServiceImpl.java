package com.example.demo.services.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.postgres.Schedule;
import com.example.demo.model.postgres.User;
import com.example.demo.repositories.postgres.ScheduleRepository;
import com.example.demo.services.interfaces.ScheduleService;
import com.example.demo.services.interfaces.UserService;
@Service
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
    private ScheduleRepository scheduleRepo;
	@Autowired
    private UserService userService;

	@Override
	public void createSchedule(Schedule newSchedule) {
		String startTime=newSchedule.getStartTime();
		String endTime=newSchedule.getEndTime();
		if(validateHour(startTime, endTime)) {
			scheduleRepo.save(newSchedule);
		}
		else {
			throw new IllegalArgumentException("La hora de fin no puede "
					+ "ser menor a la hora de inicio.");
		}
	}
	
	private boolean validateHour(String startTime, String endTime) {
		String[] hourS=startTime.split(":");
		String[] hourE=endTime.split(":");
		int startHour=Integer.parseInt(hourS[0]);
		int endHour=Integer.parseInt(hourE[0]);
		int startMin=Integer.parseInt(hourS[1]);
		int endMin=Integer.parseInt(hourE[1]);
		return 	(startHour < endHour) || 
				(startHour == endHour && startMin < endMin);
	}
	
	@Override
	public List<Schedule> getScheduleByTeacher(Integer teacherID) {
		return scheduleRepo.findByTeacher(teacherID);
	}

	@Override
	public List<Schedule> getScheduleByCourse(Integer courseID) {
		return scheduleRepo.findByCourse(courseID);
	}

	@Override
	public Schedule getScheduleByID(Integer scheduleID) {
		return scheduleRepo.findById(scheduleID).get();
	}

	@Override
	public void setTeacherFromSchedule(Integer scheduleID, Integer newteacherID) {
		Schedule schedule = getScheduleByID(scheduleID);
		User newTeacher= userService.findById(newteacherID);
		schedule.setUserCourse(newTeacher);
		scheduleRepo.save(schedule);
	}


	
}