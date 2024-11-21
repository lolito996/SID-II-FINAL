# Edutrack
EduTrack es un sistema integral de gestión educativa creado para optimizar los procesos administrativos y académicos de colegios y otras instituciones educativas. El sistema está diseñado para facilitar la gestión de docentes, materias, estudiantes y sus respectivos desempeños de manera eficiente y segura.

Funcionalidades Principales:
* Gestión de Docentes: Administra la asignación de docentes a las diferentes materias, asegurando una distribución adecuada y eficiente del personal docente.

* Gestión de Materias: Permite asignar materias tanto a docentes como a estudiantes, garantizando que cada curso esté correctamente estructurado y cada estudiante inscrito en las materias correspondientes.

* Asignación de Notas: Registra y gestiona las calificaciones de los estudiantes, permitiendo un seguimiento detallado de su rendimiento académico a lo largo del año escolar.

* Gestión de Estudiantes: Maneja el proceso de inscripción de nuevos estudiantes y la actualización de información para los que ya están matriculados, asegurando una base de datos precisa y actualizada.

* Generador de Boletín Estudiantil: Crea boletines de calificaciones de manera automática, presentando un resumen claro y organizado del rendimiento académico de los estudiantes (Opcional).
  
* Cálculo de Año Escolar: Automatiza el cálculo del progreso y cumplimiento del año escolar, tomando en cuenta la asistencia, notas y otros factores clave para determinar si un estudiante ha completado con éxito su año académico (Opcional).

## Instrucciones para ejecutar el proyecto

### Requisitos previos
1. **Instalar PostgreSQL**: Asegúrate de que PostgreSQL esté instalado en el nuevo PC.

### Crear la base de datos
1. Abre una terminal y conéctate a PostgreSQL como superusuario (por ejemplo, `postgres`):

    ```sh
    psql -U postgres
    ```

2. Ejecuta el siguiente comando SQL para crear la base de datos:

    ```sql
    CREATE DATABASE edutrack;
    ```

### Configurar la aplicación
1. Asegúrate de que el archivo `application.properties` en tu proyecto tenga la configuración correcta para la base de datos, utilizando el superusuario y su contraseña. Por defecto, el usuario y la contraseña son `postgres` y `postgres`, pero pueden ser distintos según la configuración de tu PostgreSQL:

    ```java-properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/edutrack
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    spring.datasource.driverClassName=org.postgresql.Driver

    # Configuración de JPA e Hibernate
    spring.jpa.hibernate.ddl-auto=create
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.globally_quoted_identifiers=true
    ```

### Ejecutar la aplicación
1. Para ejecutar la aplicación, puedes usar el siguiente comando en la terminal:

    ```sh
    ./gradlew bootRun
    ```

### Ejecutar los tests
1. Para ejecutar los tests, utiliza el siguiente comando en la terminal:

    ```sh
    ./gradlew test
    ```


Integrantes:
* David Malte - A00368867
* Alejandro Muñoz - A00395672
* Santiago Castillo - A00395559
* Juan Felipe Plaza - A00365963
