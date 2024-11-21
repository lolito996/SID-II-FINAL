// package com.example.demo.services.impl;

// import com.example.demo.model.Course;
// import com.example.demo.repositories.CourseRepository;
// <<<<<<< HEAD:Edutrack/src/test/java/com/example/demo/CourseServiceImpTest.java
// import com.example.demo.services.impl.CourseServiceImpl;
// import com.example.demo.services.interfaces.CourseService;
// =======
// >>>>>>> main:Edutrack/src/test/java/com/example/demo/services/impl/CourseServiceImpTest.java
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// public class CourseServiceImpTest {

//     @InjectMocks
//     private CourseServiceImpl courseService;

//     @Mock
//     private CourseRepository courseRepository;

//     public CourseServiceImpTest() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testSaveCourse() {
//         // Crear un curso simulado
//         Course course = new Course();
        
//         // Llamar al método del servicio
//         courseService.saveCourse(course);

//         // Verificar que el método save del repositorio fue llamado
//         verify(courseRepository).save(course);
//     }

//     @Test
//     public void testFindByName() {
//         // Crear un curso simulado
//         String courseName = "Mathematics";
//         Course course = new Course();
//         course.setName(courseName);
        
//         // Configurar el comportamiento del mock
//         when(courseRepository.findByName(courseName)).thenReturn(course);

//         // Llamar al método del servicio
//         Course result = courseService.findByName(courseName);

//         // Verificar el resultado
//         assertEquals(courseName, result.getName());
//     }
// }
