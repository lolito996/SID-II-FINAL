package com.example.demo.repositories.postgres;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.postgres.Schedule;

import java.util.List;
import java.util.Optional;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

	@Query(value = "Select * from schedule where "
			+ "user_course_id = ?1",
			nativeQuery = true)
	List<Schedule> findByTeacher(Integer teacherID);
	
	@Query(value = "Select * from schedule where "
			+ "course_id = ?1 ",
			nativeQuery = true)
	List<Schedule> findByCourse(Integer courseID);
	
	Optional<Schedule> findById(Integer id);
}
