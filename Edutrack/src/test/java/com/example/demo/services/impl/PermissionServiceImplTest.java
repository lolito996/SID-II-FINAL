// package com.example.demo.services.impl;

// import com.example.demo.model.Permission;
// import com.example.demo.repositories.PermissionRepository;

// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import java.util.ArrayList;
// import java.util.List;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// public class PermissionServiceImplTest {

//     @InjectMocks
//     private PermissionServiceImpl permissionService;

//     @Mock
//     private PermissionRepository permissionRepo;

//     public PermissionServiceImplTest() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testShowAllPermissions() {
//         // Crear una lista de permisos simulada
//         List<Permission> permissions = new ArrayList<>();
//         Permission permission1 = new Permission();
//         Permission permission2 = new Permission();
//         permissions.add(permission1);
//         permissions.add(permission2);

//         // Configurar el comportamiento del mock
//         when(permissionRepo.findAll()).thenReturn(permissions);

//         // Llamar al método del servicio
//         List<Permission> result = permissionService.showAllPermissions();

//         // Verificar el resultado
//         assertEquals(2,  result.size());
//         assertEquals(permission1, result.get(0));
//         assertEquals(permission2, result.get(1));
//     }

//     @Test
//     public void testSavePermission() {
//         // Crear un permiso simulado
//         Permission permission = new Permission();
        
//         // Llamar al método del servicio
//         permissionService.savePermission(permission);

//         // Verificar que el método save del repositorio fue llamado
//         verify(permissionRepo).save(permission);
//     }

//     @Test
//     public void testDeletePermission() {
//         // Crear un permiso simulado
//         Permission permission = new Permission();
        
//         // Configurar el comportamiento del mock para delete
//         doNothing().when(permissionRepo).delete(permission);
        
//         // Llamar al método del servicio
//         permissionService.deletePermission(permission);

//         // Verificar que el método delete del repositorio fue llamado
//         verify(permissionRepo).delete(permission);
//     }
// }
