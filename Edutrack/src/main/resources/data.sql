INSERT INTO course (name, capacity) VALUES
('1-A', 30),
('1-B', 30),
('8-A', 25),
('8-B', 25),
('8-C', 25);
INSERT INTO role (name, description) VALUES 
('Administrador', 'Rol dedicado a la gestión y supervisión del sistema.'),
('Profesor', 'Rol que se encarga de la enseñanza y evaluación de los estudiantes.'),
('Estudiante', 'Rol que representa a los alumnos del sistema.'),
('Coordinador', 'Rol que gestiona los programas académicos y actividades.'),
('Asistente', 'Rol que ayuda en las labores administrativas y académicas.');
INSERT INTO permissions (name, description) VALUES 
('MANAGE_USERS', 'Permite gestionar los usuarios del sistema.'),
('MANAGE_ROLES', 'Permite gestionar los roles disponibles en el sistema.'),
('MANAGE_PERMISSIONS', 'Permite gestionar los permisos asignados a los roles.'),
('COURSE_ENROLLABLE', 'Permite que un curso sea matriculable por los estudiantes.'),
('ENROLL_STUDENTS', 'Permite matricular estudiantes en cursos.'),
('ASSIGN_TEACHERS_TO_CLASSES', 'Permite asignar profesores a las clases correspondientes.'),
('GRADE_ASSIGNMENT', 'Permite asignar y gestionar las notas de los estudiantes.');
INSERT INTO rolepermissions (role_id, permissions_id) VALUES 
(1, 1),  -- Administrador: MANAGE_USERS
(1, 2),  -- Administrador: MANAGE_ROLES
(1, 3),  -- Administrador: MANAGE_PERMISSIONS
(1, 4),  -- Administrador: COURSE_ENROLLABLE
(1, 5),  -- Administrador: ENROLL_STUDENTS
(1, 6),  -- Administrador: ASSIGN_TEACHERS_TO_CLASSES
(1, 7),  -- Administrador: GRADE_ASSIGNMENT
(2, 6),  -- Profesor: ASSIGN_TEACHERS_TO_CLASSES
(2, 7),  -- Profesor: GRADE_ASSIGNMENT
(3, 5),  -- Estudiante: ENROLL_STUDENTS
(4, 6),  -- Coordinador: ASSIGN_TEACHERS_TO_CLASSES
(5, 1);  -- Asistente: MANAGE_USERS
INSERT INTO subject (name, description, minimum_passing_grade) VALUES 
('Matemáticas', 'Estudio de los números, operaciones y resolución de problemas.', 3.0),
('Lengua y Literatura', 'Desarrollo de habilidades de lectura, escritura y análisis de textos.', 3.0),
('Ciencias Naturales', 'Exploración de fenómenos naturales y el mundo que nos rodea.', 3.0),
('Historia', 'Estudio de eventos históricos y su impacto en la sociedad.', 3.0),
('Educación Física', 'Promoción de la actividad física y el deporte.', 3.0);
INSERT INTO "user" (name, identification_number, birthdate, username, password, course_id) VALUES 
('Juan Pérez', '1234567890', '2005-03-15', 'juan.perez', '{bcrypt}$2a$10$uQ1Finar3etMXR4SQPWHDOBFht2eu8jW0oIV5j7KVx78y4xJU7URG', 1),  -- Estudiante en el curso 1-A
('Carlos Zuluaga', '1234325540', '2005-07-15', 'carl.zul', '{bcrypt}$2a$10$uQ1Finar3etMXR4SQPWHDOBFht2eu8jW0oIV5j7KVx78y4xJU7URG', 3),  -- Estudiante en el curso 8-A
('María López', '0987654321', '1980-06-20', 'maria.lopez', '{bcrypt}$2a$10$uQ1Finar3etMXR4SQPWHDOBFht2eu8jW0oIV5j7KVx78y4xJU7URG', NULL),  -- Administrador
('Carlos Gómez', '1122334455', '1975-11-10', 'carlos.gomez', '{bcrypt}$2a$10$uQ1Finar3etMXR4SQPWHDOBFht2eu8jW0oIV5j7KVx78y4xJU7URG', NULL),  -- Profesor
('Ana Torres', '2233445566', '1985-02-28', 'ana.torres', '{bcrypt}$2a$10$uQ1Finar3etMXR4SQPWHDOBFht2eu8jW0oIV5j7KVx78y4xJU7URG', NULL),  -- Coordinador
('Luis Martínez', '3344556677', '1990-05-12', 'luis.martinez', '{bcrypt}$2a$10$uQ1Finar3etMXR4SQPWHDOBFht2eu8jW0oIV5j7KVx78y4xJU7URG', NULL);  -- Asistente
INSERT INTO userrole (user_id, role_id) VALUES 
(1, 1),  -- Admin (Admin) asignado como Admin
(2, 3),  -- Juan Pérez (Estudiante) asignado como Estudiante
(3, 3),  -- Carlos Zuluaga (Estudiante) asignado como Estudiante
(4, 1),  -- María López (Administrador) asignado como Administrador
(5, 2),  -- Carlos Gómez (Profesor) asignado como Profesor
(6, 4),  -- Ana Torres (Coordinador) asignado como Coordinador
(7, 5);  -- Luis Martínez (Asistente) asignado como Asistente
INSERT INTO grades (final_grade, observations, user_course_id, subject_id) VALUES 
(4.5, 'Excelente desempeño en matemáticas.', 1, 1),  -- Estudiante en curso 1-A, materia Matemáticas
(3.8, 'Buena participación en lengua y literatura.', 1, 2),  -- Estudiante en curso 1-A, materia Lengua y Literatura
(2.9, 'Necesita mejorar en ciencias naturales.', 2, 3),  -- Estudiante en curso 8-A, materia Ciencias Naturales
(4.0, 'Sólido entendimiento de la historia.', 1, 4),  -- Estudiante en curso 1-A, materia Historia
(3.5, 'Buen desempeño en educación física.', 2, 5);  -- Estudiante en curso 8-A, materia Educación Física
INSERT INTO schedule (start_time, end_time, classroom, days, course_id, subject_id, user_course_id) VALUES 
('08:00', '09:30', 'A1', 'Lunes, Miércoles', 2, 1, 3),  -- Profesor Carlos Gómez (ID 3) en Matemáticas
('10:00', '11:30', 'B2', 'Martes, Jueves', 1, 2, 3),  -- Profesor Carlos Gómez (ID 3) en Lengua y Literatura
('12:00', '13:30', 'C3', 'Lunes, Miércoles', 3, 3, 3),  -- Profesor Carlos Gómez (ID 3) en Ciencias Naturales
('14:00', '15:30', 'A1', 'Martes, Jueves', 3, 4, 3),  -- Profesor Carlos Gómez (ID 3) en Historia
('16:00', '17:30', 'B2', 'Lunes, Miércoles', 1, 5, 3);  -- Profesor Carlos Gómez (ID 3) en Educación Física















