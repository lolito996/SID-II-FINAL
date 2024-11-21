// package com.example.demo;
// import com.example.demo.model.Course;
// import com.example.demo.model.Role;
// import com.example.demo.model.User;
// import com.example.demo.model.Userrole;
// import com.example.demo.repositories.CourseRepository;
// import com.example.demo.repositories.UserRepository;
// import com.example.demo.repositories.UserRoleRepository;
// import com.example.demo.services.impl.UserServiceImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.List;
// import java.util.UUID;

// class UserServiceImplTest {

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private CourseRepository courseRepository;

//     @Mock
//     private UserRoleRepository userRoleRepository;

//     @Mock
//     private PasswordEncoder passwordEncoder;

//     @InjectMocks
//     private UserServiceImpl userService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     // Existing tests...

//     @Test
//     void testAssignCourseStudent() {
//         // Arrange
//         String username = "testuser";
//         User user = new User();
//         when(userRepository.findByUsername(username)).thenReturn(user);

//         // Act
//         User result = userService.findByUsername(username);

//         // Assert
//         assertEquals(user, result);
//         verify(userRepository, times(1)).findByUsername(username);
//     }

//     /*
//      * Test para UpdateUser()
//      */
//     @Test
//     void updateUser_NotImplemented() {
//         // Act
//         userService.UpdateUser("testuser",new User());

//         // No hay implementación todavía
//     }

//     /*
//      * Test para deleteUser()
//      */
//     @Test
//     void deleteUser_NotImplemented() {
//         // Act
//         userService.deleteUser("testuser");

//         // No hay implementación todavía
//     }

//     /*
//      * Test para authenticateUser()
//      */
//     @Test
//     void authenticateUser_ShouldReturnTrueWhenCredentialsAreCorrect() {
//         // Arrange
//         String username = "testuser";
//         String password = "password123";
//         User user = new User();
//         user.setPassword(password);
//         when(userRepository.findByUsername(username)).thenReturn(user);

//         // Act
//         boolean result = userService.authenticateUser(username, password);

//         // Assert
//         assertTrue(result);
//     }

//     @Test
//     void authenticateUser_ShouldReturnFalseWhenCredentialsAreIncorrect() {
//         // Arrange
//         String username = "testuser";
//         String password = "password123";
//         User user = new User();
//         user.setPassword("wrongpassword");
//         when(userRepository.findByUsername(username)).thenReturn(user);

//         // Act
//         boolean result = userService.authenticateUser(username, password);

//         // Assert
//         assertFalse(result);
//     }

//     /*
//      * Test para assignCourseStudent()
//      */
//     @Test
//     void assignCourseStudent_ShouldAssignCourseToUser() {
//         // Arrange
//         String username = "testuser";
//         String courseName = "Mathematics";
//         User user = new User();
//         Course course = new Course();
//         course.setName(courseName);

//         when(userRepository.findByUsername(username)).thenReturn(user);
//         when(courseRepository.findByName(courseName)).thenReturn(course);

//         // Act
//         userService.assignCourseStudent(username, courseName);

//         // Assert
//         verify(userRepository, times(1)).save(user);
//         assertEquals(course, user.getCourse());
//     }

//     @Test
//     void testAssignCourseStudentWithNonExistingUser() {
//         // Arrange
//         String username = "nonexistentuser";
//         String courseName = "Mathematics";
//         when(userRepository.findByUsername(username)).thenReturn(null);

//         // Act
//         userService.assignCourseStudent(username, courseName);

//         // Assert
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testAssignCourseStudentWithNonExistingCourse() {
//         // Arrange
//         String username = "testuser";
//         String courseName = "NonexistentCourse";
//         User user = new User();
//         when(userRepository.findByUsername(username)).thenReturn(user);
//         when(courseRepository.findByName(courseName)).thenReturn(null);

//         // Act
//         userService.assignCourseStudent(username, courseName);

//         // Assert
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testAssignRole() {
//         // Arrange
//         Userrole userRole = new Userrole();

//         // Act
//         userService.assignRole(userRole);

//         // Assert
//         verify(userRoleRepository, times(1)).save(userRole);
//     }

//     @Test
//     void testDeleteRole() {
//         // Arrange
//         Userrole userRole = new Userrole();

//         // Act
//         userService.deleteRole(userRole);

//         // Assert
//         verify(userRoleRepository, times(1)).delete(userRole);
//     }

//     @Test
//     void testFindByRole() {
//         // Arrange
//         Role role = new Role();
//         Userrole userRole1 = new Userrole();
//         Userrole userRole2 = new Userrole();
//         User user1 = new User();
//         User user2 = new User();
//         userRole1.setUserCourse(user1);
//         userRole2.setUserCourse(user2);
//         List<Userrole> userRoles = Arrays.asList(userRole1, userRole2);

//         when(userRoleRepository.findByRole(role)).thenReturn(userRoles);

//         // Act
//         List<User> users = userService.findByRole(role);

//         // Assert
//         assertEquals(2, users.size());
//         assertTrue(users.contains(user1));
//         assertTrue(users.contains(user2));
//     }

//     @Test
//     void testFindByRoleWithNoRoles() {
//         // Arrange
//         Role role = new Role();
//         when(userRoleRepository.findByRole(role)).thenReturn(Arrays.asList());

//         // Act
//         List<User> users = userService.findByRole(role);

//         // Assert
//         assertTrue(users.isEmpty());
//     }

//     @Test
//     void testUpdateUser() {
//         // Arrange
//         User existingUser = new User();
//         existingUser.setUsername("testuser");
//         existingUser.setName("Old Name");
//         existingUser.setIdentificationNumber("1234567890");
//         existingUser.setBirthdate(LocalDate.of(2000, 1, 1));
//         existingUser.setCourse(new Course());
        
//         User updatedUser = new User();
//         updatedUser.setUsername("testuser");
//         updatedUser.setName("New Name");
//         updatedUser.setIdentificationNumber("0987654321");
//         updatedUser.setBirthdate(LocalDate.of(1999, 12, 31));
//         updatedUser.setCourse(new Course());

//         when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

//         // Act
//         userService.updateUser(updatedUser);

//         // Assert
//         verify(userRepository, times(1)).save(existingUser);
//         assertEquals("New Name", existingUser.getName());
//         assertEquals("0987654321", existingUser.getIdentificationNumber());
//         assertEquals(LocalDate.of(1999, 12, 31), existingUser.getBirthdate());
//     }

//     @Test
//     void testUpdateUserWithNonExistingUser() {
//         // Arrange
//         User updatedUser = new User();
//         updatedUser.setUsername("nonexistentuser");

//         when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

//         // Act
//         userService.updateUser(updatedUser);

//         // Assert
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testDeleteUser() {
//         // Arrange
//         User user = new User();
//         user.setId(UUID.randomUUID().toString());
//         user.setUsername("testuser");

//         when(userRepository.findByUsername("testuser")).thenReturn(user);

//         // Act
//         userService.deleteUser("testuser");

//         // Assert
//         verify(userRepository, times(1)).deleteById(user.getId());
//     }

//     @Test
//     void testDeleteUserWithNonExistingUser() {
//         // Arrange
//         when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

//         // Act
//         userService.deleteUser("nonexistentuser");

//         // Assert
//         verify(userRepository, never()).deleteById(anyString());
//     }

//     @Test
//     void testRegisterUserWithNullCourse() {
//         // Arrange
//         String username = "testuser";
//         String password = "testpassword";
//         String name = "John Doe";
//         String identificationNumber = "1234567890";
//         LocalDate birthdate = LocalDate.of(2000, 1, 1);
//         String courseName = "NonexistentCourse";

//         when(userRepository.findByUsername(username)).thenReturn(null);
//         when(courseRepository.findByName(courseName)).thenReturn(null);

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.registerUser(username, password, name, identificationNumber, birthdate, courseName);
//         });
//         assertEquals("El curso especificado no existe", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithValidDataAndCourse() {
//         // Arrange
//         String username = "testuser";
//         String password = "testpassword";
//         String name = "John Doe";
//         String identificationNumber = "1234567890";
//         LocalDate birthdate = LocalDate.of(2000, 1, 1);
//         String courseName = "Mathematics";
//         Course course = new Course();
//         course.setName(courseName);

//         when(userRepository.findByUsername(username)).thenReturn(null);
//         when(courseRepository.findByName(courseName)).thenReturn(course);
//         when(passwordEncoder.encode(password)).thenReturn("encodedpassword");
//         when(userRepository.save(any(User.class))).thenReturn(new User());

//         // Act
//         userService.registerUser(username, password, name, identificationNumber, birthdate, courseName);

//         // Assert
//         verify(userRepository, times(1)).save(any(User.class));
//     }

//     // @Test
//     // void testAssignRoleWithNullUser() {
//     //     // Arrange
//     //     Userrole userRole = new Userrole();
//     //     userRole.setUserCourse(null);

//     //     // Act
//     //     userService.assignRole(userRole);

//     //     // Assert
//     //     verify(userRoleRepository, never()).save(userRole);
//     // }

//     // @Test
//     // void testAssignRoleWithNullRole() {
//     //     // Arrange
//     //     Userrole userRole = new Userrole();
//     //     userRole.setRole(null);

//     //     // Act
//     //     userService.assignRole(userRole);

//     //     // Assert
//     //     verify(userRoleRepository, never()).save(userRole);
//     // }

//     @Test
//     void testDeleteUserWithNullUser() {
//         // Arrange
//         when(userRepository.findByUsername("testuser")).thenReturn(null);

//         // Act
//         userService.deleteUser("testuser");

//         // Assert
//         verify(userRepository, never()).deleteById(anyString());
//     }

//     // @Test
//     // void testUpdateUserWithNullData() {
//     //     // Arrange
//     //     User updatedUser = new User();
//     //     updatedUser.setUsername(null);

//     //     // Act & Assert
//     //     IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//     //         userService.updateUser(updatedUser);
//     //     });
//     //     assertEquals("El nombre de usuario no puede estar vacío", exception.getMessage());

//     //     verify(userRepository, never()).save(any(User.class));
//     // }

//     @Test
//     void testAuthenticateUserWithEmptyUsername() {
//         // Arrange
//         String username = "";
//         String password = "testpassword";

//         // Act
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.authenticateUser(username, password);
//         });

//         // Assert
//         assertEquals("El nombre de usuario no puede estar vacío", exception.getMessage());
//         verify(userRepository, never()).findByUsername(username);
//     }

//     @Test
//     void testAuthenticateUserWithEmptyPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = "";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.authenticateUser(username, password);
//         });
//         assertEquals("La contraseña no puede estar vacía", exception.getMessage());

//         verify(userRepository, never()).findByUsername(username);
//     }


//     @Test
//     void testRegisterUserWithEmptyName() {
//         // Arrange
//         String username = "testuser";
//         String password = "testpassword";
//         String name = "";
//         String identificationNumber = "1234567890";
//         LocalDate birthdate = LocalDate.of(2000, 1, 1);
//         String courseName = "Mathematics";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.registerUser(username, password, name, identificationNumber, birthdate, courseName);
//         });
//         assertEquals("El nombre no puede estar vacío", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(userRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithEmptyIdentificationNumber() {
//         // Arrange
//         String username = "testuser";
//         String password = "testpassword";
//         String name = "John Doe";
//         String identificationNumber = ""; // empty identification number
//         LocalDate birthdate = LocalDate.of(2000, 1, 1);
//         String courseName = "Mathematics";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.registerUser(username, password, name, identificationNumber, birthdate, courseName);
//         });
//         assertEquals("El número de identificación debe tener 10 caracteres", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(userRepository, never()).save(any(User.class));
//     }


//     @Test
//     void testAuthenticateUserWithIncorrectUsernameFormat() {
//         // Arrange
//         String username = "invalid!username";
//         String password = "testpassword";

//         // Act
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.authenticateUser(username, password);
//         });
//         // Assert
//         assertEquals("El nombre de usuario no puede contener caracteres especiales", exception.getMessage());
//         verify(userRepository, never()).findByUsername(username);
//     }

//     @Test
//     void testAuthenticateUserWithIncorrectPasswordFormat() {
//         // Arrange
//         String username = "testuser";
//         String password = "short"; // short password

//         // Act
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.authenticateUser(username, password);
//         });

//         // Assert
//         assertEquals("La contraseña debe tener al menos 8 caracteres", exception.getMessage());
//         verify(userRepository, never()).findByUsername(username);
//     }

//     @Test
//     void testRegisterUserWithFutureBirthdate() {
//         // Arrange
//         String username = "testuser";
//         String password = "testpassword";
//         String name = "John Doe";
//         String identificationNumber = "1234567890";
//         LocalDate futureBirthdate = LocalDate.of(2050, 1, 1); // future date
//         String courseName = "Mathematics";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.registerUser(username, password, name, identificationNumber, futureBirthdate, courseName);
//         });
//         assertEquals("La fecha de nacimiento no puede ser una fecha futura", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(userRepository, never()).save(any(User.class));
//     }
// }
