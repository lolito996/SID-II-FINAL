package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exeptions.IllegalArgumentException;
import com.example.demo.model.postgres.Course;
import com.example.demo.model.postgres.Role;
import com.example.demo.model.postgres.User;
import com.example.demo.model.postgres.Userrole;
import com.example.demo.repositories.postgres.CourseRepository;
import com.example.demo.repositories.postgres.UserRepository;
import com.example.demo.repositories.postgres.UserRoleRepository;
import com.example.demo.services.interfaces.RoleService;
import com.example.demo.services.interfaces.UserService;

import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private RoleService roleService;

    
    //Constraseñas
	private PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // Obtener el encoder directamente
	}

    /*
	 * Manage User
	 */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> findAllUsers() {
        return userRepository.findAll(); // Asegúrate de que userRepository tenga este método
    }

	
	@Override
	public void UpdateUser (String username, User updatedUser) {

        User existingUser = findByUsername(username);
		if (existingUser != null) {

			existingUser.setName(updatedUser.getName());
			existingUser.setId(updatedUser.getId());
			existingUser.setCourse(updatedUser.getCourse());
			existingUser.setIdentificationNumber(updatedUser.getIdentificationNumber());
			existingUser.setBirthdate(updatedUser.getBirthdate());
			existingUser.setGrades(updatedUser.getGrades());
			existingUser.setPassword(updatedUser.getPassword());
			existingUser.setUsername(updatedUser.getUsername());

			userRepository.save(existingUser);
		}else {
			throw new IllegalArgumentException("Not found the user in the BD");
		}
	}
	
	
	/*
	 * Manage sesion
	 */

    @Override
    public void updateUser(User updatedUser) {
        User existingUser = findByUsername(updatedUser.getUsername());
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setIdentificationNumber(updatedUser.getIdentificationNumber());
            existingUser.setBirthdate(updatedUser.getBirthdate());
            existingUser.setCourse(updatedUser.getCourse());
            userRepository.save(existingUser);
        }
    }

    @Override
    public void deleteUser(String username) {
        User user = findByUsername(username);
        if (user != null) {
            userRepository.deleteById(user.getId());
        }
    }

    /*
     * Registro y Autenticación de Usuario
     */
    @Override
    public void registerUser(String username, String password, String name, String identificationNumber,
            LocalDate birthdate, String courseName) {
        // Validar que el nombre de usuario no esté vacío
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }

        // Validar que la contraseña no esté vacía y tenga una longitud mínima
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }

        // Validar que el nombre no esté vacío
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        // Validar que el número de identificación tenga 10 caracteres
        if (identificationNumber == null || identificationNumber.trim().isEmpty() || identificationNumber.length() != 10) {
            throw new IllegalArgumentException("El número de identificación debe tener 10 caracteres");
        }

        // Validar que la fecha de nacimiento no sea nula y que sea una fecha válida en el pasado
        if (birthdate == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
        }
        if (birthdate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura");
        }

        // Verificar si el usuario ya existe
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }


        // Crear el nuevo usuario
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder().encode(password)); // Codificar la contraseña
        user.setName(name);
        user.setIdentificationNumber(identificationNumber);
        user.setBirthdate(birthdate);
        //user.setCourse(course); // Asignar el curso

        // Guardar el nuevo usuario
        userRepository.save(user);
    }



    
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
            
        }
        List<String> roles = roleService.getUserPermissions(user).stream().map(p -> p.getName()).toList();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(roles.toArray(new String[roles.size()]))
            .build();
        System.out.println(roles);
        return userDetails;
	}

    
    /*
     * Manejo de Estudiantes
     */
    @Override
    @PreAuthorize("hasAnyRole('MANAGE_USERS')")
    public void assignCourseStudent(String name, String courseName) {
        User usertemp = findByUsername(name);
        Course course = courseRepo.findByName(courseName);
        if (usertemp != null && course != null) {
            usertemp.setCourse(course);
            userRepository.save(usertemp);
            
        }
    }
    @PostConstruct
    public void init() {
    	try {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder().encode("admin"));
            user.setName("Alberto Admin");
            user.setIdentificationNumber("123456");
            user.setBirthdate(LocalDate.of(1990, 1, 1));
            user.setCourse(null);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el stack trace completo de la excepción
        }
 }

	/*
	 * Manage User-Rol
	 */
	@Override
    @Transactional
    public void assignRole(Userrole userRole) {
        if (userRole == null || userRole.getId() == null) {
            throw new IllegalArgumentException("Userrole or its ID cannot be null");
        }

        // Verifica que el usuario y el rol existan antes de guardar
        User user = userRole.getUser();
        Role role = userRole.getRole();

        if (user == null || role == null) {
            throw new IllegalArgumentException("User or Role cannot be null in Userrole");
        }

        // Guarda la relación en la base de datos
        userRoleRepository.save(userRole);
    }


	@Override
	public void deleteRole(Userrole userRole) {
		userRoleRepository.delete(userRole);
	}
	@Override
	public List<User> findByRole(Role rolName) {
		List<Userrole> userRolList = userRoleRepository.findByRole(rolName);
		List<User> userList = new ArrayList<User>();
		
		for (Userrole userRol : userRolList) {
			userList.add(userRol.getUser());
		}
		return userList;
	}

    @Override
    public Map<String, Object> getAllUsersWithRoles() {
        List<User> users = userRepository.findAll();
        Map<Integer, List<Role>> userRoles = new HashMap<>();
        
        for (User user : users) {
            List<Role> roles = getRolesForUser(user);
            userRoles.put(user.getId(), roles);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("userRoles", userRoles);
        
        return result;
    }

    public List<Role> getRolesForUser(User user) {
        List<Userrole> userroles = userRoleRepository.findByUser(user);
        return userroles.stream().map(Userrole::getRole).collect(Collectors.toList());
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void removeRole(Integer userId, Integer roleId) {
        // Llamar al repositorio para eliminar el Userrole basado en userId y roleId
        userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
    }
    
}