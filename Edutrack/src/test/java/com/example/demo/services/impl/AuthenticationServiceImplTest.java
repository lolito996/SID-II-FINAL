// package com.example.demo.services.impl;

// import com.example.demo.model.User;
// import com.example.demo.repositories.AuthenticationRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;

// class AuthenticationServiceImplTest {

//     @Mock
//     private AuthenticationRepository authenticationRepository;

//     @Mock
//     private PasswordEncoder passwordEncoder;

//     @InjectMocks
//     private AuthenticationServiceImpl authenticationService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testRegisterUser() {
//         // Arrange
//         String username = "testuser";
//         String password = "testpassword";
//         String encodedPassword = "encodedpassword";

//         when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
//         when(authenticationRepository.save(any(User.class))).thenReturn(new User());

//         // Act
//         authenticationService.registerUser(username, password);

//         // Assert
//         verify(passwordEncoder, times(1)).encode(password);
//         verify(authenticationRepository, times(1)).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithEmptyUsername() {
//         // Arrange
//         String username = "";
//         String password = "testpassword";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Username cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithEmptyPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = "";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Password cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithNullUsername() {
//         // Arrange
//         String username = null;
//         String password = "testpassword";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Username cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithNullPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = null;

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Password cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithExistingUsername() {
//         // Arrange
//         String username = "existinguser";
//         String password = "testpassword";
//         String encodedPassword = "encodedpassword";

//         when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
//         when(authenticationRepository.findByUsername(username)).thenReturn(new User());

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Username already exists", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testAuthenticateUser() {
//         // Arrange
//         String username = "testuser";
//         String password = "testpassword";
//         String encodedPassword = "encodedpassword";

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(encodedPassword);

//         when(authenticationRepository.findByUsername(username)).thenReturn(user);
//         when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNotNull(authenticatedUser);
//         assertEquals(username, authenticatedUser.getUsername());
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, times(1)).matches(password, encodedPassword);
//     }

//     @Test
//     void testAuthenticateUserWithInvalidPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = "wrongpassword";
//         String encodedPassword = "encodedpassword";

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(encodedPassword);

//         when(authenticationRepository.findByUsername(username)).thenReturn(user);
//         when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNull(authenticatedUser);
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, times(1)).matches(password, encodedPassword);
//     }

//     @Test
//     void testAuthenticateUserWithNonExistingUsername() {
//         // Arrange
//         String username = "nonexistinguser";
//         String password = "testpassword";

//         when(authenticationRepository.findByUsername(username)).thenReturn(null);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNull(authenticatedUser);
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, never()).matches(anyString(), anyString());
//     }

//     @Test
//     void testRegisterUserWithSpecialCharactersInUsername() {
//         // Arrange
//         String username = "user!@#";
//         String password = "testpassword";
//         String encodedPassword = "encodedpassword";

//         when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
//         when(authenticationRepository.save(any(User.class))).thenReturn(new User());

//         // Act
//         authenticationService.registerUser(username, password);

//         // Assert
//         verify(passwordEncoder, times(1)).encode(password);
//         verify(authenticationRepository, times(1)).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithSpecialCharactersInPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = "pass!@#";
//         String encodedPassword = "encodedpassword";

//         when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
//         when(authenticationRepository.save(any(User.class))).thenReturn(new User());

//         // Act
//         authenticationService.registerUser(username, password);

//         // Assert
//         verify(passwordEncoder, times(1)).encode(password);
//         verify(authenticationRepository, times(1)).save(any(User.class));
//     }

//     @Test
//     void testAuthenticateUserWithSpecialCharactersInUsername() {
//         // Arrange
//         String username = "user!@#";
//         String password = "testpassword";
//         String encodedPassword = "encodedpassword";

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(encodedPassword);

//         when(authenticationRepository.findByUsername(username)).thenReturn(user);
//         when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNotNull(authenticatedUser);
//         assertEquals(username, authenticatedUser.getUsername());
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, times(1)).matches(password, encodedPassword);
//     }

//     @Test
//     void testAuthenticateUserWithSpecialCharactersInPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = "pass!@#";
//         String encodedPassword = "encodedpassword";

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(encodedPassword);

//         when(authenticationRepository.findByUsername(username)).thenReturn(user);
//         when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNotNull(authenticatedUser);
//         assertEquals(username, authenticatedUser.getUsername());
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, times(1)).matches(password, encodedPassword);
//     }

//     @Test
//     void testRegisterUserWithLongUsername() {
//         // Arrange
//         String username = "a".repeat(256);
//         String password = "testpassword";
//         String encodedPassword = "encodedpassword";

//         when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
//         when(authenticationRepository.save(any(User.class))).thenReturn(new User());

//         // Act
//         authenticationService.registerUser(username, password);

//         // Assert
//         verify(passwordEncoder, times(1)).encode(password);
//         verify(authenticationRepository, times(1)).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithLongPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = "a".repeat(256);
//         String encodedPassword = "encodedpassword";

//         when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
//         when(authenticationRepository.save(any(User.class))).thenReturn(new User());

//         // Act
//         authenticationService.registerUser(username, password);

//         // Assert
//         verify(passwordEncoder, times(1)).encode(password);
//         verify(authenticationRepository, times(1)).save(any(User.class));
//     }

//     @Test
//     void testAuthenticateUserWithLongUsername() {
//         // Arrange
//         String username = "a".repeat(256);
//         String password = "testpassword";
//         String encodedPassword = "encodedpassword";

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(encodedPassword);

//         when(authenticationRepository.findByUsername(username)).thenReturn(user);
//         when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNotNull(authenticatedUser);
//         assertEquals(username, authenticatedUser.getUsername());
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, times(1)).matches(password, encodedPassword);
//     }

//     @Test
//     void testAuthenticateUserWithLongPassword() {
//         // Arrange
//         String username = "testuser";
//         String password = "a".repeat(256);
//         String encodedPassword = "encodedpassword";

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(encodedPassword);

//         when(authenticationRepository.findByUsername(username)).thenReturn(user);
//         when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNotNull(authenticatedUser);
//         assertEquals(username, authenticatedUser.getUsername());
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, times(1)).matches(password, encodedPassword);
//     }

//     @Test
//     void testRegisterUserWithWhitespaceUsername() {
//         // Arrange
//         String username = " ";
//         String password = "testpassword";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Username cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithWhitespacePassword() {
//         // Arrange
//         String username = "testuser";
//         String password = " ";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Password cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testAuthenticateUserWithWhitespaceUsername() {
//         // Arrange
//         String username = " ";
//         String password = "testpassword";

//         when(authenticationRepository.findByUsername(username)).thenReturn(null);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNull(authenticatedUser);
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, never()).matches(anyString(), anyString());
//     }

//     @Test
//     void testAuthenticateUserWithWhitespacePassword() {
//         // Arrange
//         String username = "testuser";
//         String password = " ";

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword("encodedpassword");

//         when(authenticationRepository.findByUsername(username)).thenReturn(user);
//         when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNull(authenticatedUser);
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, times(1)).matches(password, user.getPassword());
//     }

//     @Test
//     void testRegisterUserWithNullUsernameAndPassword() {
//         // Arrange
//         String username = null;
//         String password = null;

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Username cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testRegisterUserWithEmptyUsernameAndPassword() {
//         // Arrange
//         String username = "";
//         String password = "";

//         // Act & Assert
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             authenticationService.registerUser(username, password);
//         });
//         assertEquals("Username cannot be null or empty", exception.getMessage());

//         verify(passwordEncoder, never()).encode(anyString());
//         verify(authenticationRepository, never()).save(any(User.class));
//     }

//     @Test
//     void testAuthenticateUserWithNullUsernameAndPassword() {
//         // Arrange
//         String username = null;
//         String password = null;

//         when(authenticationRepository.findByUsername(username)).thenReturn(null);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNull(authenticatedUser);
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, never()).matches(anyString(), anyString());
//     }

//     @Test
//     void testAuthenticateUserWithEmptyUsernameAndPassword() {
//         // Arrange
//         String username = "";
//         String password = "";

//         when(authenticationRepository.findByUsername(username)).thenReturn(null);

//         // Act
//         User authenticatedUser = authenticationService.authenticateUser(username, password);

//         // Assert
//         assertNull(authenticatedUser);
//         verify(authenticationRepository, times(1)).findByUsername(username);
//         verify(passwordEncoder, never()).matches(anyString(), anyString());
//     }
// }