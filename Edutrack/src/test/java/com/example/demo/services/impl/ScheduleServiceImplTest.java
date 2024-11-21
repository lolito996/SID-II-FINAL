// package com.example.demo.services.impl;

// import com.example.demo.model.Schedule;
// import com.example.demo.repositories.ScheduleRepository;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import java.util.ArrayList;
// import java.util.List;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.when;

// public class ScheduleServiceImplTest {

//     @InjectMocks
//     private ScheduleServiceImpl scheduleService;

//     @Mock
//     private ScheduleRepository scheduleRepo;

//     public ScheduleServiceImplTest() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testShowAllSchedules() {
//         // Crear una lista de horarios simulada
//         List<Schedule> schedules = new ArrayList<>();
//         Schedule schedule1 = new Schedule();
//         Schedule schedule2 = new Schedule();
//         schedules.add(schedule1);
//         schedules.add(schedule2);

//         // Configurar el comportamiento del mock
//         when(scheduleRepo.findAll()).thenReturn(schedules);

//         // Llamar al método del servicio
//         List<Schedule> result = scheduleService.showAllSchedules();

//         // Verificar el resultado
//         assertEquals(2, result.size());
//         assertEquals(schedule1, result.get(0));
//         assertEquals(schedule2, result.get(1));
//     }
// }
