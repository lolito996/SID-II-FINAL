// package com.example.demo;

// import com.example.demo.model.Permission;
// import com.example.demo.model.Role;
// import com.example.demo.model.Rolepermission;
// import com.example.demo.repositories.RolePermissionRepository;
// import com.example.demo.repositories.RoleRepository;
// import com.example.demo.repositories.PermissionRepository;
// import com.example.demo.services.impl.RoleServiceImpl;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import static org.mockito.Mockito.*;

// public class RolePermissionServiceImplTest {

//     @InjectMocks
//     private RoleServiceImpl rolePermissionService;

//     @Mock
//     private RolePermissionRepository rolePermissionRepo;

//     @Mock
//     private RoleRepository roleRepo;

//     @Mock
//     private PermissionRepository permissionRepo;

//     public RolePermissionServiceImplTest() {
//         MockitoAnnotations.openMocks(this);
//     }

//     private Role role;
//     private Permission permission;

//     @BeforeEach
//     public void setUp() {
//         // Inicializar Role y Permission
//         role = new Role();
//         role.setId(1);
//         role.setName("ROLE_USER");

//         permission = new Permission();
//         permission.setId(1);
//         permission.setName("READ_PRIVILEGES");
//     }

//     @Test
//     public void testAddPermission() {
//         // Crear un Rolepermission simulado
//         Rolepermission rolePermission = new Rolepermission();
//         rolePermission.setRole(role);
//         rolePermission.setPermissions(permission);

//         // Configurar el comportamiento del mock para verificar la existencia de Role y Permission
//         when(roleRepo.existsById(role.getId())).thenReturn(true);
//         when(permissionRepo.existsById(permission.getId())).thenReturn(true);

//         // Llamar al método del servicio
//         rolePermissionService.addPermission(rolePermission);

//         // Verificar que el método save del repositorio fue llamado
//         verify(rolePermissionRepo).save(rolePermission);
//     }
// }
