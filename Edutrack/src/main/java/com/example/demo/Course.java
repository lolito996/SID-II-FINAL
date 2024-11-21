// package com.example.demo.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "course")
// public class Course {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "id", nullable = false, length = 10)
//     private Integer id;

//     @Column(name = "name", nullable = false, length = 25)
//     private String name;

//     @Column(name = "capacity", nullable = false)
//     private Integer capacity;

// 	public Integer getId() {
// 		return id;
// 	}

// 	public void setId(Integer id) {
// 		this.id = id;
// 	}

// 	public String getName() {
// 		return name;
// 	}

// 	public void setName(String name) {
// 		this.name = name;
// 	}

// 	public Integer getCapacity() {
// 		return capacity;
// 	}

// 	public void setCapacity(Integer capacity) {
// 		this.capacity = capacity;
// 	}

// }

// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// import java.math.BigDecimal;

// @Getter
// @Setter
// @Entity
// @Table(name = "grades")
// public class Grade {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "grade_id", nullable = false, length = Integer.MAX_VALUE)
//     private Integer id;

//     @Column(name = "final_grade", nullable = false)
//     private BigDecimal finalGrade;

//     @Column(name = "observations", length = Integer.MAX_VALUE)
//     private String observations;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "user_course_id")
//     private User user;
    
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "subject_id")
//     private Subject subjectGrade;

// 	public Integer getId() {
// 		return id;
// 	}

// 	public void setId(Integer id) {
// 		this.id = id;
// 	}

// 	public BigDecimal getFinalGrade() {
// 		return finalGrade;
// 	}

// 	public void setFinalGrade(BigDecimal finalGrade) {
// 		this.finalGrade = finalGrade;
// 	}

// 	public String getObservations() {
// 		return observations;
// 	}

// 	public void setObservations(String observations) {
// 		this.observations = observations;
// 	}

// 	public User getUser() {
// 		return user;
// 	}

// 	public void setUser(User userCourse) {
// 		this.user = userCourse;
// 	}

// }

// package com.example.demo.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "permissions")
// public class Permission {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "permissions_id", nullable = false)
//     private Integer id;

//     @Column(name = "description", length = Integer.MAX_VALUE)
//     private String description;

//     @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
//     private String name;


// 	public Integer getId() {
// 		return id;
// 	}

// 	public void setId(Integer id) {
// 		this.id = id;
// 	}

// 	public String getDescription() {
// 		return description;
// 	}

// 	public void setDescription(String description) {
// 		this.description = description;
// 	}

// 	public String getName() {
// 		return name;
// 	}

// 	public void setName(String name) {
// 		this.name = name;
// 	}

// }

// package com.example.demo.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import jakarta.persistence.CascadeType;

// import lombok.Getter;
// import lombok.Setter;

// import java.util.List;
// import java.util.stream.Collectors;

// @Getter
// @Setter
// @Entity
// @Table(name = "role")
// public class Role {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "role_id", nullable = false)
//     private Integer id;

// 	@Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
//     private String name;

//     @Column(name = "description", length = Integer.MAX_VALUE)
//     private String description;

//     @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
//     private List<Rolepermission> rolepermissions;

//     public String getName() {
// 		return name;
// 	}

// 	public void setName(String name) {
// 		this.name = name;
// 	}

// 	public String getDescription() {
// 		return description;
// 	}

// 	public void setDescription(String description) {
// 		this.description = description;
// 	}

// 	public List<Rolepermission> getRolepermissions() {
// 		return rolepermissions;
// 	}

// 	public void setRolepermissions(List<Rolepermission> rolepermissions) {
// 		this.rolepermissions = rolepermissions;
// 	}

// 	public void setId(Integer id) {
// 		this.id = id;
// 	}

// 	public List<Permission> getPermissions() {
//         return rolepermissions.stream()
//                               .map(Rolepermission::getPermissions)
//                               .collect(Collectors.toList());
//     }
	

//     public Integer getId() {
// 		return id;
// 	}
// }


// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "rolepermissions")
// public class Rolepermission {
//     @EmbeddedId
//     private RolepermissionId id;

//     @MapsId("roleId")
//     @ManyToOne(fetch = FetchType.LAZY, optional = false)
//     @JoinColumn(name = "role_id", nullable = false)
//     private Role role;

//     @MapsId("permissionsId")
//     @ManyToOne(fetch = FetchType.LAZY, optional = false)
//     @JoinColumn(name = "permissions_id", nullable = false)
//     private Permission permission;


// 	public RolepermissionId getId() {
// 		return id;
// 	}

// 	public void setId(RolepermissionId id) {
// 		this.id = id;
// 	}

// 	public Role getRole() {
// 		return role;
// 	}

// 	public void setRole(Role role) {
// 		this.role = role;
// 	}

// 	public Permission getPermissions() {
// 		return permission;
// 	}

// 	public void setPermissions(Permission permission) {
// 		this.permission = permission;
// 	}

// }

// package com.example.demo.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Embeddable;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import lombok.Getter;
// import lombok.Setter;
// import org.hibernate.Hibernate;

// import java.io.Serializable;
// import java.util.Objects;

// @Getter
// @Setter
// @Embeddable
// public class RolepermissionId implements Serializable {
//     private static final long serialVersionUID = -8361773536897746941L;
//     @Column(name = "role_id", nullable = false)
//     private Integer roleId;

//     @Column(name = "permissions_id", nullable = false)
//     private Integer permissionsId;

//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (!(o instanceof RolepermissionId)) return false;
//         RolepermissionId that = (RolepermissionId) o;
//         return Objects.equals(roleId, that.roleId) && 
//                Objects.equals(permissionsId, that.permissionsId);
//     }

//    @Override
//     public int hashCode() {
//         return Objects.hash(roleId, permissionsId);
//     }

//     public void setPermissionId(Integer permissionId) {
//         this.permissionsId = permissionId;
//     }

// 	public void setRoleId(Integer roleId) {
// 		this.roleId = roleId;
// 	}

// }

// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "schedule")
// public class Schedule {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
//     private Integer id;

//     @Column(name = "start_time", length = Integer.MAX_VALUE)
//     private String startTime;

//     @Column(name = "end_time", length = Integer.MAX_VALUE)
//     private String endTime;

//     @Column(name = "classroom", length = Integer.MAX_VALUE)
//     private String classroom;

//     @Column(name = "days", length = Integer.MAX_VALUE)
//     private String days;

//     @ManyToOne(fetch = FetchType.LAZY, optional = false)
//     @JoinColumn(name = "course_id", nullable = false)
//     private Course course;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "subject_id")
//     private Subject subject;

//     @ManyToOne(fetch = FetchType.LAZY, optional = false)
//     @JoinColumn(name = "user_course_id", nullable = false)
//     private User userCourse;

// 	public Integer getId() {
// 		return id;
// 	}

// 	public void setId(Integer id) {
// 		this.id = id;
// 	}

// 	public String getStartTime() {
// 		return startTime;
// 	}

// 	public void setStartTime(String startTime) {
// 		this.startTime = startTime;
// 	}

// 	public String getEndTime() {
// 		return endTime;
// 	}

// 	public void setEndTime(String endTime) {
// 		this.endTime = endTime;
// 	}

// 	public String getClassroom() {
// 		return classroom;
// 	}

// 	public void setClassroom(String classroom) {
// 		this.classroom = classroom;
// 	}

// 	public String getDays() {
// 		return days;
// 	}

// 	public void setDays(String days) {
// 		this.days = days;
// 	}

// 	public Course getCourse() {
// 		return course;
// 	}

// 	public void setCourse(Course course) {
// 		this.course = course;
// 	}

// 	public Subject getSubject() {
// 		return subject;
// 	}

// 	public void setSubject(Subject subject) {
// 		this.subject = subject;
// 	}

// 	public User getUserCourse() {
// 		return userCourse;
// 	}

// 	public void setUserCourse(User userCourse) {
// 		this.userCourse = userCourse;
// 	}
// }

// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// import java.math.BigDecimal;

// @Getter
// @Setter
// @Entity
// @Table(name = "subject")
// public class Subject {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "subject_id", nullable = false, length = Integer.MAX_VALUE)
//     private Integer id;

//     @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
//     private String name;

//     @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
//     private String description;

//     @Column(name = "minimum_passing_grade")
//     private BigDecimal minimumPassingGrade;

// 	public Integer getId() {
// 		return id;
// 	}

// 	public void setId(Integer id) {
// 		this.id = id;
// 	}

// 	public String getName() {
// 		return name;
// 	}

// 	public void setName(String name) {
// 		this.name = name;
// 	}

// 	public String getDescription() {
// 		return description;
// 	}

// 	public void setDescription(String description) {
// 		this.description = description;
// 	}

// 	public BigDecimal getMinimumPassingGrade() {
// 		return minimumPassingGrade;
// 	}

// 	public void setMinimumPassingGrade(BigDecimal minimumPassingGrade) {
// 		this.minimumPassingGrade = minimumPassingGrade;
// 	}

	

// }

// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// import java.time.LocalDate;
// import java.util.List;


// @Getter
// @Setter
// @Entity
// @Table(name = "\"User\"")
// public class User {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
//     private Integer id;

//     @Column(name = "name", length = Integer.MAX_VALUE)
//     private String name;

//     @Column(name = "identification_number", length = 10)
//     private String identificationNumber;

//     @Column(name = "birthdate")
//     private LocalDate birthdate;
    
//     @Column(name = "username", length = Integer.MAX_VALUE)
//     private String username;

//     @Column(name = "password", length = Integer.MAX_VALUE)
//     private String password;


//     @ManyToOne(fetch = FetchType.LAZY, optional = true)
//     @JoinColumn(name = "course_id", nullable = true)
//     private Course course;

// 	@OneToMany(mappedBy = "user")
//     private List<Grade> grades;

// 	public List<Grade> getGrades() {
// 		return grades;
// 	}

// 	public void setGrades(List<Grade> grades) {
// 		this.grades = grades;
// 	}

// 	public void setCourse(Course course) {
// 		this.course = course;
// 	}

// 	public Integer getId() {
// 		return id;
// 	}

// 	public void setId(Integer id) {
// 		this.id = id;
// 	}

// 	public String getName() {
// 		return name;
// 	}

// 	public void setName(String name) {
// 		this.name = name;
// 	}

// 	public String getIdentificationNumber() {
// 		return identificationNumber;
// 	}

// 	public void setIdentificationNumber(String identificationNumber) {
// 		this.identificationNumber = identificationNumber;
// 	}

// 	public LocalDate getBirthdate() {
// 		return birthdate;
// 	}

// 	public void setBirthdate(LocalDate birthdate) {
// 		this.birthdate = birthdate;
// 	}

// 	public String getUsername() {
// 		return username;
// 	}

// 	public void setUsername(String username) {
// 		this.username = username;
// 	}

// 	public String getPassword() {
// 		return password;
// 	}

// 	public void setPassword(String password) {
// 		this.password = password;
// 	}

// 	public Course getCourse() {
// 		return course;
// 	}
    
    

// }

// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @Entity
// @Table(name = "userrole")
// public class Userrole {
//     @EmbeddedId
//     private UserroleId id;

//     @MapsId("userCourseId")
// 	@ManyToOne(fetch = FetchType.LAZY, optional = false)
// 	@JoinColumn(name = "user_id", nullable = false)
// 	private User user;

// 	@MapsId("roleId")
// 	@ManyToOne(fetch = FetchType.LAZY, optional = false)
// 	@JoinColumn(name = "role_id", nullable = false)
// 	private Role role;



// 	public UserroleId getId() {
// 		return id;
// 	}

// 	public void setId(UserroleId id) {
// 		this.id = id;
// 	}

// 	public User getUser() {
// 		return user;
// 	}

// 	public void setUser(User userCourse) {
// 		this.user = userCourse;
// 	}

// 	public Role getRole() {
// 		return role;
// 	}

// 	public void setRole(Role role) {
// 		this.role = role;
// 	}

// }

// package com.example.demo.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Embeddable;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import lombok.Getter;
// import lombok.Setter;
// import org.hibernate.Hibernate;

// import java.io.Serializable;
// import java.util.Objects;

// @Getter
// @Setter
// @Embeddable
// public class UserroleId implements Serializable {
//     private static final long serialVersionUID = 3095484627834070549L;
    
//     @Column(name = "user_id", nullable = false, length = 10)
//     private Integer userCourseId;
    
// 	@Column(name = "role_id", nullable = false)
//     private Integer roleId;
	
//     public Integer getUserCourseId() {
// 		return userCourseId;
// 	}

// 	public void setUserCourseId(Integer userCourseId) {
// 		this.userCourseId = userCourseId;
// 	}

// 	public Integer getRoleId() {
// 		return roleId;
// 	}

// 	public void setRoleId(Integer roleId) {
// 		this.roleId = roleId;
// 	}



//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//         UserroleId entity = (UserroleId) o;
//         return Objects.equals(this.userCourseId, entity.userCourseId) &&
//                 Objects.equals(this.roleId, entity.roleId);
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(userCourseId, roleId);
//     }

// }


// package com.example.demo.repositories;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.math.BigDecimal;
// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;
// import java.util.List;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

// import java.util.List;
// import java.util.Optional;


// @Repository
// public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

// 	@Query(value = "Select * from schedule where "
// 			+ "user_course_id = ?1",
// 			nativeQuery = true)
// 	List<Schedule> findByTeacher(Integer teacherID);
	
// 	@Query(value = "Select * from schedule where "
// 			+ "course_id = ?1 ",
// 			nativeQuery = true)
// 	List<Schedule> findByCourse(Integer courseID);
	
// 	Optional<Schedule> findById(Integer id);
// }


// package com.example.demo.repositories;


// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import org.springframework.transaction.annotation.Transactional;
// @Repository
// public interface UserRepository extends JpaRepository<User, Integer> {

//     // Devuelve un Optional para manejo seguro de nulos
//     User findByUsername(String username);
//     @Transactional
//     void deleteById(@SuppressWarnings("null") Integer id);
// }


// package com.example.demo.repositories;

// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;
// import java.math.BigDecimal;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.services.interfaces.GradeService;

// @Service
// public class GradeServiceImpl implements GradeService{
	
// 	@Autowired
// 	private GradeRepository gradeRepository;

// 	@Override
// 	public void assigGrade(Grade grade) {
// 		gradeRepository.save(grade);
// 	}

// 	@Override
// 	public BigDecimal findGradeStudentSubject(Integer studentId, Integer subjectId) {
// 		return gradeRepository.findGradeByStudentSubject(studentId, subjectId);
// 	}

// 	@Override
// 	public List<Grade> findAllGradeStudent(Integer studentId) {
// 		return gradeRepository.findAllGradeByStudent(studentId);
// 	}

// 	@Override
// 	public void updateGradeStudentSubject(Integer studentId, Integer subjectId, BigDecimal newGrade) {
// 		Grade gradeStudent= gradeRepository.getGradeStudenSubject(studentId, subjectId);
// 		gradeStudent.setFinalGrade(newGrade);
// 		assigGrade(gradeStudent);
// 	}

// }

// package com.example.demo.services.impl;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.services.interfaces.SubjectService;

// @Service
// public class SubjectServiceImpl implements SubjectService{

// 	@Autowired
// 	private SubjectRepository subjectRepo;
	
// 	@Override
// 	public List<Subject> showAllSubjects() {
// 		return subjectRepo.findAll();
// 	}

// 	@Override
// 	public Subject findByName(String name) {
// 		return subjectRepo.findByName(name);
// 	}

// 	@Override
// 	public void save(Subject subject) {
// 		subjectRepo.save(subject);
// 	}

// 	@Override
// 	public void delete(Subject subject) {
// 		subjectRepo.delete(subject);
// 	}


// }

// package com.example.demo.services.impl;

// import java.util.ArrayList;
// import java.util.List;
// import java.time.LocalDate;
// import java.util.stream.Collectors;
// import java.util.Map;
// import java.util.HashMap;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.example.demo.exeptions.IllegalArgumentException;
// import com.example.demo.model.postgres.Course;
// import com.example.demo.model.postgres.Grade;
// import com.example.demo.model.postgres.Permission;
// import com.example.demo.model.postgres.Role;
// import com.example.demo.model.postgres.Rolepermission;
// import com.example.demo.model.postgres.RolepermissionId;
// import com.example.demo.model.postgres.Schedule;
// import com.example.demo.model.postgres.Subject;
// import com.example.demo.model.postgres.User;
// import com.example.demo.model.postgres.Userrole;
// import com.example.demo.model.postgres.UserroleId;
// import com.example.demo.repositories.postgres.CourseRepository;
// import com.example.demo.repositories.postgres.GradeRepository;
// import com.example.demo.repositories.postgres.SubjectRepository;
// import com.example.demo.repositories.postgres.UserRepository;
// import com.example.demo.repositories.postgres.UserRoleRepository;
// import com.example.demo.services.interfaces.RoleService;
// import com.example.demo.services.interfaces.UserService;

// import jakarta.transaction.Transactional;
// import jakarta.annotation.PostConstruct;

// @Service
// public class UserServiceImpl implements UserService, UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private UserRoleRepository userRoleRepository;

//     @Autowired
//     private CourseRepository courseRepo;
//     @Autowired
//     private RoleService roleService;

    
//     //Constraseñas
// 	private PasswordEncoder passwordEncoder() {
// 	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // Obtener el encoder directamente
// 	}

//     /*
// 	 * Manage User
// 	 */
//     @Override
//     public User findByUsername(String username) {
//         return userRepository.findByUsername(username);
//     }

//     @Override
//     public List<User> getAllUsers() {
//         return userRepository.findAll();
//     }
    
//     public List<User> findAllUsers() {
//         return userRepository.findAll(); // Asegúrate de que userRepository tenga este método
//     }

	
// 	@Override
// 	public void UpdateUser (String username, User updatedUser) {

//         User existingUser = findByUsername(username);
// 		if (existingUser != null) {

// 			existingUser.setName(updatedUser.getName());
// 			existingUser.setId(updatedUser.getId());
// 			existingUser.setCourse(updatedUser.getCourse());
// 			existingUser.setIdentificationNumber(updatedUser.getIdentificationNumber());
// 			existingUser.setBirthdate(updatedUser.getBirthdate());
// 			existingUser.setGrades(updatedUser.getGrades());
// 			existingUser.setPassword(updatedUser.getPassword());
// 			existingUser.setUsername(updatedUser.getUsername());

// 			userRepository.save(existingUser);
// 		}else {
// 			throw new IllegalArgumentException("Not found the user in the BD");
// 		}
// 	}
	
	
// 	/*
// 	 * Manage sesion
// 	 */

//     @Override
//     public void updateUser(User updatedUser) {
//         User existingUser = findByUsername(updatedUser.getUsername());
//         if (existingUser != null) {
//             existingUser.setName(updatedUser.getName());
//             existingUser.setIdentificationNumber(updatedUser.getIdentificationNumber());
//             existingUser.setBirthdate(updatedUser.getBirthdate());
//             existingUser.setCourse(updatedUser.getCourse());
//             userRepository.save(existingUser);
//         }
//     }

//     @Override
//     public void deleteUser(String username) {
//         User user = findByUsername(username);
//         if (user != null) {
//             userRepository.deleteById(user.getId());
//         }
//     }

//     /*
//      * Registro y Autenticación de Usuario
//      */
//     @Override
//     public void registerUser(String username, String password, String name, String identificationNumber,
//             LocalDate birthdate, String courseName) {
//         // Validar que el nombre de usuario no esté vacío
//         if (username == null || username.trim().isEmpty()) {
//             throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
//         }

//         // Validar que la contraseña no esté vacía y tenga una longitud mínima
//         if (password == null || password.trim().isEmpty()) {
//             throw new IllegalArgumentException("La contraseña no puede estar vacía");
//         }
//         if (password.length() < 8) {
//             throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
//         }

//         // Validar que el nombre no esté vacío
//         if (name == null || name.trim().isEmpty()) {
//             throw new IllegalArgumentException("El nombre no puede estar vacío");
//         }

//         // Validar que el número de identificación tenga 10 caracteres
//         if (identificationNumber == null || identificationNumber.trim().isEmpty() || identificationNumber.length() != 10) {
//             throw new IllegalArgumentException("El número de identificación debe tener 10 caracteres");
//         }

//         // Validar que la fecha de nacimiento no sea nula y que sea una fecha válida en el pasado
//         if (birthdate == null) {
//             throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
//         }
//         if (birthdate.isAfter(LocalDate.now())) {
//             throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura");
//         }

//         // Verificar si el usuario ya existe
//         if (userRepository.findByUsername(username) != null) {
//             throw new IllegalArgumentException("El nombre de usuario ya existe");
//         }


//         // Crear el nuevo usuario
//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(passwordEncoder().encode(password)); // Codificar la contraseña
//         user.setName(name);
//         user.setIdentificationNumber(identificationNumber);
//         user.setBirthdate(birthdate);
//         //user.setCourse(course); // Asignar el curso

//         // Guardar el nuevo usuario
//         userRepository.save(user);
//     }



    
//     @Override
// 	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByUsername(username);
//         if(user == null) {
//             throw new UsernameNotFoundException("User not found");
            
//         }
//         List<String> roles = roleService.getUserPermissions(user).stream().map(p -> p.getName()).toList();
//         UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//             .username(user.getUsername())
//             .password(user.getPassword())
//             .roles(roles.toArray(new String[roles.size()]))
//             .build();
//         System.out.println(roles);
//         return userDetails;
// 	}

    
//     /*
//      * Manejo de Estudiantes
//      */
//     @Override
//     @PreAuthorize("hasAnyRole('MANAGE_USERS')")
//     public void assignCourseStudent(String name, String courseName) {
//         User usertemp = findByUsername(name);
//         Course course = courseRepo.findByName(courseName);
//         if (usertemp != null && course != null) {
//             usertemp.setCourse(course);
//             userRepository.save(usertemp);
            
//         }
//     }
//     @PostConstruct
//     public void init() {
//     	try {
//             User user = new User();
//             user.setUsername("admin");
//             user.setPassword(passwordEncoder().encode("admin"));
//             user.setName("Alberto Admin");
//             user.setIdentificationNumber("123456");
//             user.setBirthdate(LocalDate.of(1990, 1, 1));
//             user.setCourse(null);
//             userRepository.save(user);
//         } catch (Exception e) {
//             e.printStackTrace(); // Esto imprimirá el stack trace completo de la excepción
//         }
//  }

// 	/*
// 	 * Manage User-Rol
// 	 */
// 	@Override
//     @Transactional
//     public void assignRole(Userrole userRole) {
//         if (userRole == null || userRole.getId() == null) {
//             throw new IllegalArgumentException("Userrole or its ID cannot be null");
//         }

//         // Verifica que el usuario y el rol existan antes de guardar
//         User user = userRole.getUser();
//         Role role = userRole.getRole();

//         if (user == null || role == null) {
//             throw new IllegalArgumentException("User or Role cannot be null in Userrole");
//         }

//         // Guarda la relación en la base de datos
//         userRoleRepository.save(userRole);
//     }


// 	@Override
// 	public void deleteRole(Userrole userRole) {
// 		userRoleRepository.delete(userRole);
// 	}
// 	@Override
// 	public List<User> findByRole(Role rolName) {
// 		List<Userrole> userRolList = userRoleRepository.findByRole(rolName);
// 		List<User> userList = new ArrayList<User>();
		
// 		for (Userrole userRol : userRolList) {
// 			userList.add(userRol.getUser());
// 		}
// 		return userList;
// 	}

//     @Override
//     public Map<String, Object> getAllUsersWithRoles() {
//         List<User> users = userRepository.findAll();
//         Map<Integer, List<Role>> userRoles = new HashMap<>();
        
//         for (User user : users) {
//             List<Role> roles = getRolesForUser(user);
//             userRoles.put(user.getId(), roles);
//         }
        
//         Map<String, Object> result = new HashMap<>();
//         result.put("users", users);
//         result.put("userRoles", userRoles);
        
//         return result;
//     }

//     public List<Role> getRolesForUser(User user) {
//         List<Userrole> userroles = userRoleRepository.findByUser(user);
//         return userroles.stream().map(Userrole::getRole).collect(Collectors.toList());
//     }

//     @Override
//     public User findById(Integer id) {
//         return userRepository.findById(id).orElse(null);
//     }

//     @Transactional
//     public void removeRole(Integer userId, Integer roleId) {
//         // Llamar al repositorio para eliminar el Userrole basado en userId y roleId
//         userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
//     }
    
// }

