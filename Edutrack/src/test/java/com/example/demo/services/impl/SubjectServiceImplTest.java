// package com.example.demo.services.impl;
// import com.example.demo.model.Subject;
// import com.example.demo.repositories.SubjectRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;

// public class SubjectServiceImplTest {

//     @InjectMocks
//     private SubjectServiceImpl subjectService;

//     @Mock
//     private SubjectRepository subjectRepo;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testShowAllSubjects() {
//         // Arrange
//         List<Subject> subjects = new ArrayList<>();
//         subjects.add(new Subject()); // Add a sample subject
//         when(subjectRepo.findAll()).thenReturn(subjects);

//         // Act
//         List<Subject> result = subjectService.showAllSubjects();

//         // Assert
//         assertEquals(subjects, result);
//         verify(subjectRepo, times(1)).findAll();
//     }

//     @Test
//     public void testFindByName() {
//         // Arrange
//         String name = "Math";
//         Subject subject = new Subject();
//         subject.setName(name);
//         when(subjectRepo.findByName(name)).thenReturn(subject);

//         // Act
//         Subject result = subjectService.findByName(name);

//         // Assert
//         assertEquals(subject, result);
//         verify(subjectRepo, times(1)).findByName(name);
//     }

//     @Test
//     public void testSave() {
//         // Arrange
//         Subject subject = new Subject();

//         // Act
//         subjectService.save(subject);

//         // Assert
//         verify(subjectRepo, times(1)).save(subject);
//     }

//     @Test
//     public void testDelete() {
//         // Arrange
//         Subject subject = new Subject();

//         // Act
//         subjectService.delete(subject);

//         // Assert
//         verify(subjectRepo, times(1)).delete(subject);
//     }
// }
