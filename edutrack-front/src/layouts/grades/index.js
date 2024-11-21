import React, { useEffect, useState } from "react";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import DataTable from "examples/Tables/DataTable";
import axiosInstance from "../../services/axios";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import {
  Card,
  Grid,
  TextField,
  Button,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
} from "@mui/material";
import MDButton from "components/MDButton";

const GradesView = () => {
  const [grades, setGrades] = useState([]);
  const [students, setStudents] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState(""); // Nuevo estado para el estudiante seleccionado
  const [newGrade, setNewGrade] = useState({
    student: "",
    subject: "",
    grade: "",
    observations: "",
  });
  const [gradeToUpdate, setGradeToUpdate] = useState({
    student: "",
    subject: "",
    grade: "",
    observations: "",
  });

  // Fetch calificaciones, estudiantes y materias
  useEffect(() => {
    const fetchData = async () => {
      try {
        const studentsResponse = await axiosInstance.get("/role/all-students");
        setStudents(studentsResponse.data.students);

        const subjectsResponse = await axiosInstance.get("/subject/subjects");
        setSubjects(subjectsResponse.data);

        if (studentsResponse.data.students.length > 0) {
          const defaultStudentId = studentsResponse.data.students[0].id;
          setSelectedStudent(defaultStudentId); // Seleccionar el primer estudiante por defecto
          fetchGrades(defaultStudentId);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  // Fetch grades for a specific student
  const fetchGrades = async (studentId) => {
    try {
      const gradesResponse = await axiosInstance.get(`/grade/student/${studentId}`);
      console.log(gradesResponse.data);
      setGrades(gradesResponse.data);
    } catch (error) {
      console.error("Error fetching grades:", error);
    }
  };

  // Manejo de la creación de nuevas calificaciones
  const handleAddGrade = async () => {
    try {
      console.log(newGrade);
      const response = await axiosInstance.post("/grade/grade", newGrade);
      alert(response.data);
      fetchGrades(selectedStudent); // Actualizar las calificaciones para el estudiante seleccionado
      setNewGrade({ student: "", subject: "", grade: "", observations: "" });
    } catch (error) {
      console.error("Error adding grade:", error);
      alert(error.response.data);
    }
  };

  const handleUpdateGrade = async () => {
    try {
      const { student, subject, grade, observations } = gradeToUpdate;
      console.log(gradeToUpdate);
      const response = await axiosInstance.put(
        `/grade/grade/student/${student}/subject/${subject}`,
        {
          student,
          subject,
          grade,
          observations,
        }
      );
      alert(response.data);
      fetchGrades(selectedStudent);
      setGradeToUpdate({ student: "", subject: "", grade: "" });
    } catch (error) {
      console.error("Error updating grade:", error);
      alert(error.response?.data || "Error al actualizar la calificación");
    }
  };

  // Generar columnas y filas para DataTable
  const columns = [
    { Header: "Estudiante", accessor: "student", align: "left" },
    { Header: "Materia", accessor: "subject", align: "left" },
    { Header: "Calificación", accessor: "grade", align: "center" },
    { Header: "Observaciones", accessor: "observations", align: "left" },
    { Header: "¿Aprobó?", accessor: "pass", align: "center" },
  ];

  const rows = grades.map((grade) => ({
    student: grade.student,
    subject: grade.subject,
    grade: grade.grade,
    observations: grade.observations,
    pass: grade.pass ? "Sí" : "No",
  }));

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          {/* Tabla de calificaciones */}
          <Grid item xs={12}>
            <Card>
              <MDBox
                mx={2}
                mt={-3}
                py={3}
                px={2}
                variant="gradient"
                bgColor="info"
                borderRadius="lg"
                coloredShadow="info"
              >
                <MDTypography variant="h6" color="white">
                  Tabla de Calificaciones
                </MDTypography>
              </MDBox>
              <MDBox px={3} py={2}>
                <FormControl fullWidth margin="normal">
                  <InputLabel>Seleccionar Estudiante</InputLabel>
                  <Select
                    value={selectedStudent}
                    onChange={(e) => {
                      setSelectedStudent(e.target.value);
                      fetchGrades(e.target.value);
                    }}
                  >
                    {students.map((student) => (
                      <MenuItem key={student.id} value={student.id}>
                        {student.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              </MDBox>
              <MDBox pt={3}>
                <DataTable
                  table={{ columns, rows }}
                  isSorted={false}
                  entriesPerPage={false}
                  showTotalEntries={false}
                  noEndBorder
                />
              </MDBox>
            </Card>
          </Grid>

          {/* Formulario para agregar calificaciones */}
          <Grid item xs={12} md={6}>
            <Card>
              <MDBox mx={3} my={2}>
                <MDTypography variant="h6" gutterBottom>
                  Agregar Calificación
                </MDTypography>
                <FormControl fullWidth margin="normal">
                  <InputLabel>Estudiante</InputLabel>
                  <Select
                    value={newGrade.student}
                    onChange={(e) => setNewGrade({ ...newGrade, student: e.target.value })}
                  >
                    {students.map((student) => (
                      <MenuItem key={student.username} value={student.username}>
                        {student.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
                <FormControl fullWidth margin="normal">
                  <InputLabel>Materia</InputLabel>
                  <Select
                    value={newGrade.subject}
                    onChange={(e) => setNewGrade({ ...newGrade, subject: e.target.value })}
                  >
                    {subjects.map((subject) => (
                      <MenuItem key={subject.name} value={subject.name}>
                        {subject.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
                <TextField
                  fullWidth
                  label="Calificación"
                  type="number"
                  variant="outlined"
                  value={newGrade.grade}
                  onChange={(e) => setNewGrade({ ...newGrade, grade: e.target.value })}
                  margin="normal"
                />
                <TextField
                  fullWidth
                  label="Observaciones"
                  variant="outlined"
                  value={newGrade.observations}
                  onChange={(e) => setNewGrade({ ...newGrade, observations: e.target.value })}
                  margin="normal"
                />
                <MDButton variant="gradient" color="info" onClick={handleAddGrade} sx={{ mt: 2 }}>
                  Agregar Calificación
                </MDButton>
              </MDBox>
            </Card>
          </Grid>
          <Grid item xs={12} md={6}>
            <Card>
              <MDBox mx={3} my={2}>
                <MDTypography variant="h6" gutterBottom>
                  Actualizar Calificación
                </MDTypography>
                <FormControl fullWidth margin="normal">
                  <InputLabel>Estudiante</InputLabel>
                  <Select
                    value={gradeToUpdate.student}
                    onChange={(e) =>
                      setGradeToUpdate({ ...gradeToUpdate, student: e.target.value })
                    }
                  >
                    {students.map((student) => (
                      <MenuItem key={student.id} value={student.id}>
                        {student.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
                <FormControl fullWidth margin="normal">
                  <InputLabel>Materia</InputLabel>
                  <Select
                    value={gradeToUpdate.subject}
                    onChange={(e) =>
                      setGradeToUpdate({ ...gradeToUpdate, subject: e.target.value })
                    }
                  >
                    {subjects.map((subject) => (
                      <MenuItem key={subject.id} value={subject.id}>
                        {subject.name}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <TextField
                  fullWidth
                  label="Calificación"
                  type="number"
                  variant="outlined"
                  value={gradeToUpdate.grade}
                  onChange={(e) => setGradeToUpdate({ ...gradeToUpdate, grade: e.target.value })}
                  margin="normal"
                />
                {/*
                <TextField
                  fullWidth
                  label="Observaciones"
                  variant="outlined"
                  value={gradeToUpdate.observations}
                  onChange={(e) =>
                    setGradeToUpdate({ ...gradeToUpdate, observations: e.target.value })
                  }
                  margin="normal"
                />
             */}

                <MDButton
                  variant="gradient"
                  color="info"
                  onClick={handleUpdateGrade}
                  sx={{ mt: 2 }}
                >
                  Actualizar Calificación
                </MDButton>
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
    </DashboardLayout>
  );
};

export default GradesView;
