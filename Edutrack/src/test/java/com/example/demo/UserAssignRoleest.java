// package com.example.demo;

// import static org.mockito.Mockito.*;
// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.example.demo.model.Role;
// import com.example.demo.model.User;
// import com.example.demo.model.Userrole;
// import com.example.demo.model.UserroleId;
// import com.example.demo.repositories.RoleRepository;
// import com.example.demo.repositories.UserRepository;
// import com.example.demo.repositories.UserRoleRepository;
// import com.example.demo.services.impl.UserServiceImpl;

// import java.util.Optional;

// public class UserAssignRoleest {

//     @Mock
//     private UserRoleRepository userRoleRepository;

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private RoleRepository roleRepository;

//     @InjectMocks
//     private UserServiceImpl userService;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testAssignRole_Success() {
//         // Crear un objeto User
//         User user = new User();
//         user.setId("userId");

//         // Crear un objeto Role
//         Role role = new Role();
//         role.setId(1);

//         // Crear un objeto Userrole
//         UserroleId userroleId = new UserroleId();
//         userroleId.setUserCourseId("userId");
//         userroleId.setRoleId(1);
        
//         Userrole userrole = new Userrole();
//         userrole.setId(userroleId);
//         userrole.setUserCourse(user);
//         userrole.setRole(role);

//         // Configurar el comportamiento de los mocks
//         when(userRepository.findById("userId")).thenReturn(Optional.of(user));
//         when(roleRepository.findById(1)).thenReturn(Optional.of(role));

//         // Llamar al método del servicio
//         userService.assignRole(userrole);

//         // Verificar que el método save fue llamado con el objeto userrole
//         verify(userRoleRepository, times(1)).save(userrole);
//     }

//     @Test
//     public void testAssignRole_UserNotFound() {
//         Userrole userrole = new Userrole();
//         userrole.setId(new UserroleId());

//         // Configurar el comportamiento de los mocks
//         when(userRepository.findById("userId")).thenReturn(Optional.empty());

//         // Verificar que se lanza una IllegalArgumentException
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.assignRole(userrole);
//         });

//         assertEquals("User or Role cannot be null in Userrole", exception.getMessage());
//     }

//     @Test
//     public void testAssignRole_RoleNotFound() {
//         User user = new User();
//         user.setId("userId");

//         Userrole userrole = new Userrole();
//         userrole.setId(new UserroleId());
//         userrole.setUserCourse(user);

//         // Configurar el comportamiento de los mocks
//         when(userRepository.findById("userId")).thenReturn(Optional.of(user));
//         when(roleRepository.findById(1)).thenReturn(Optional.empty());

//         // Verificar que se lanza una IllegalArgumentException
//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             userService.assignRole(userrole);
//         });

//         assertEquals("User or Role cannot be null in Userrole", exception.getMessage());
//     }
// }
