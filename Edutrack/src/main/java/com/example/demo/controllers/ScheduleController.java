
package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;


import com.example.demo.DTO.ScheduleDTO;
import com.example.demo.model.postgres.Course;
import com.example.demo.model.postgres.Schedule;
import com.example.demo.model.postgres.Subject;
import com.example.demo.model.postgres.User;
import com.example.demo.services.interfaces.ScheduleService;	
import com.example.demo.services.interfaces.CourseService;
import com.example.demo.services.interfaces.SubjectService;
import com.example.demo.services.interfaces.UserService;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	CourseService courseService;
	@Autowired
	SubjectService subjectService;
	@Autowired
	UserService userService;
	
	@GetMapping("/teacher/{id}")
	@PreAuthorize("hasRole('ASSIGN_TEACHERS_TO_CLASSES')")
	public List<ScheduleDTO> shecduleTeacher(@PathVariable("id") Integer id){
		List<Schedule> schedules=scheduleService.getScheduleByTeacher(id);
		
		return schedules.stream()
					.map(schedule -> {
						ScheduleDTO dto = new ScheduleDTO();
						dto.setStartTime(schedule.getStartTime());
						dto.setEndTime(schedule.getEndTime());
						dto.setClassroom(schedule.getClassroom());
						dto.setDays(schedule.getDays());
						dto.setCourse(schedule.getCourse().getName()); 
						dto.setSubject(schedule.getSubject().getName());
						dto.setUserCourse(schedule.getUserCourse().getName()); 
						return dto;
					})
					.collect(Collectors.toList());


	}
	
	@GetMapping("course/{id}")
	@PreAuthorize("hasRole('ASSIGN_TEACHERS_TO_CLASSES') or hasRole('ENROLL_STUDENTS')")
	public List<ScheduleDTO> scheduleCourse(@PathVariable("id") Integer id){
		List<Schedule> schedules=scheduleService.getScheduleByCourse(id);
		
		return schedules.stream()
					.map(schedule -> {
						ScheduleDTO dto = new ScheduleDTO();
						dto.setStartTime(schedule.getStartTime());
						dto.setEndTime(schedule.getEndTime());
						dto.setClassroom(schedule.getClassroom());
						dto.setDays(schedule.getDays());
						dto.setCourse(schedule.getCourse().getName()); 
						dto.setSubject(schedule.getSubject().getName());
						dto.setUserCourse(schedule.getUserCourse().getName()); 
						return dto;
					})
					.collect(Collectors.toList());
		
	}


	@PostMapping("/create")
	@PreAuthorize("hasRole('ASSIGN_TEACHERS_TO_CLASSES')")
	public ResponseEntity<String> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
		try {
			Schedule newSchedule = new Schedule();
			newSchedule.setStartTime(scheduleDTO.getStartTime());
			newSchedule.setEndTime(scheduleDTO.getEndTime());
			newSchedule.setClassroom(scheduleDTO.getClassroom());
			newSchedule.setDays(scheduleDTO.getDays());

			// Aquí se deben obtener las entidades `Course`, `Subject` y `User` según los datos de `scheduleDTO`
			Course course = courseService.findByName(scheduleDTO.getCourse());
			Subject subject = subjectService.findByName(scheduleDTO.getSubject());
			User teacher = userService.findByUsername(scheduleDTO.getUserCourse());

			if (course == null || subject == null || teacher == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
									.body("Curso, materia o docente no encontrado");
			}

			newSchedule.setCourse(course);
			newSchedule.setSubject(subject);
			newSchedule.setUserCourse(teacher);

			scheduleService.createSchedule(newSchedule);
			return ResponseEntity.status(HttpStatus.CREATED).body("Horario creado exitosamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Error al crear el horario: " + e.getMessage());
		}
	}


	@PutMapping("/{scheduleID}/teacher")
	@PreAuthorize("hasRole('ASSIGN_TEACHERS_TO_CLASSES')")
	public ResponseEntity<String> updateTeacherInSchedule(
			@PathVariable Integer scheduleID,
			@RequestParam Integer newTeacherID) {
		try {
			// Obtener el profesor (User) por el nuevo ID proporcionado
			User newTeacher = userService.findById(newTeacherID);
			if (newTeacher == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
									.body("Profesor no encontrado con ID: " + newTeacherID);
			}

			// Llamar al servicio para actualizar el profesor en el Schedule
			scheduleService.setTeacherFromSchedule(scheduleID, newTeacherID);
			return ResponseEntity.ok("Profesor actualizado exitosamente en el horario.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body("Error al actualizar el profesor en el horario: " + e.getMessage());
		}
	}
}


