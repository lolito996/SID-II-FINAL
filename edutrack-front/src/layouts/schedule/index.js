import React, { useEffect, useState } from "react";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import DataTable from "examples/Tables/DataTable";
import MDInput from "components/MDInput";
import MDButton from "components/MDButton";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import { Card, Grid, Switch } from "@mui/material";
import Footer from "examples/Footer";
import axiosInstance from "../../services/axios";

// Componente principal
const ScheduleTable = () => {
  const [schedules, setSchedules] = useState([]);
  const [teacherUsername, setTeacherUsername] = useState("");
  const [schedule, setSchedule] = useState({
    startTime: "",
    endTime: "",
    classroom: "",
    days: "",
    course: "",
    subject: "",
    userCourse: "",
  });
  const [activeRowIndex, setActiveRowIndex] = useState(null); // Estado para controlar la fila activa

  // Fetch schedules from the API
  useEffect(() => {
    const fetchSchedules = async () => {
      try {
        const response = await axiosInstance.get("/schedule/teacher/3"); // Asume el ID del teacher para este ejemplo
        setSchedules(response.data);
      } catch (error) {
        console.error("Error fetching schedules:", error);
      }
    };

    fetchSchedules();
  }, []);

  const [professors, setProfessors] = useState([]);
  useEffect(() => {
    const fetchProfessors = async () => {
      try {
        const response = await axiosInstance.get("/role/all-professors");
        console.log(response.data.professors);
        setProfessors(response.data.professors);
      } catch (error) {
        console.error(error.response.data);
      }
    };
    fetchProfessors();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setSchedule({
      ...schedule,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosInstance.post("/schedule/", schedule);
      console.log(response);
      alert(response.data); // Muestra mensaje de éxito
    } catch (error) {
      console.log(error.response.data);
      alert(error.response.data); // Muestra error si falla
    }
  };

  const handleDelete = async (id) => {
    try {
      await axiosInstance.delete("schedule/", { data: { id } });
      setSchedules((prevSchedules) => prevSchedules.filter((schedule) => schedule.id !== id));
    } catch (error) {
      alert(error.response.data);
    }
  };

  const handleTeacherChange = async (rowIndex, teacherID) => {
    try {
      // Obtener el schedule ID desde el índice de la fila
      const scheduleID = rowIndex + 1;

      // Realizar la actualización del profesor en el horario
      const response = await axiosInstance.put(
        `/schedule/${scheduleID}/teacher?newTeacherID=${teacherID}`
      );

      console.log(response);
      alert(response.data); // Muestra mensaje de éxito
    } catch (error) {
      alert(error.response.data); // Muestra error si falla
    }
  };

  // Definir columnas para la tabla
  const columns = [
    { Header: "Start Time", accessor: "startTime", align: "left" },
    { Header: "End Time", accessor: "endTime", align: "left" },
    { Header: "Classroom", accessor: "classroom", align: "left" },
    { Header: "Days", accessor: "days", align: "left" },
    { Header: "Course", accessor: "course", align: "left" },
    { Header: "Subject", accessor: "subject", align: "left" },
    { Header: "Teacher", accessor: "userCourse", align: "left" },
    { Header: "editing", accessor: "editing", align: "center" },
    { Header: "Action", accessor: "action", align: "center" },
  ];

  const rows = schedules.map((schedule, index) => ({
    startTime: schedule.startTime,
    endTime: schedule.endTime,
    classroom: schedule.classroom,
    days: schedule.days,
    course: schedule.course,
    subject: schedule.subject,
    userCourse: schedule.userCourse,
    editing: (
      <Switch
        checked={activeRowIndex === index} // Si es la fila activa, el switch estará activado
        onChange={() => setActiveRowIndex(activeRowIndex === index ? null : index)} // Alterna la fila activa
        color="primary"
      />
    ),
    action: (
      <>
        {activeRowIndex == index && (
          <MDInput
            select
            label="Seleccionar profesor"
            fullWidth
            value={""}
            onChange={(e) => {
              setTeacherUsername(e.target.value);
              handleTeacherChange(index, e.target.value);
            }}
            required
            SelectProps={{
              native: true, // Para un dropdown estilo HTML
            }}
            InputLabelProps={{
              shrink: true, // Elimina el problema de superposición
            }}
          >
            <option value="">Seleccionar</option>
            {professors.map(
              (professor) => (
                console.log(professor.username),
                (
                  <option key={professor.username} value={professor.username}>
                    {professor.name}
                  </option>
                )
              )
            )}
          </MDInput>
        )}
      </>
      /*
      <MDBox>
        <MDButton
          color="info"
          variant="gradient"
          size="small"
          onClick={() => handleTeacherChange(index)} // Usar el índice de la fila como scheduleID
        >
          Cambiar Profesor
        </MDButton>
      </MDBox>
      <MDBox display="flex" justifyContent="center">
        <MDBox display="flex" flexDirection="column" alignItems="center">
          {activeRowIndex === index && ( // Solo mostrar el campo de texto si esta fila está activa
            <MDBox display="flex" alignItems="center">
              {" "}
              <MDInput
                type="text"
                label="Teacher Username"
                fullWidth
                value={teacherUsername}
                onChange={(e) => setTeacherUsername(e.target.value)}
                sx={{ marginRight: 2 }} // Espaciado entre los campos
              />
            </MDBox>
          )}
        </MDBox>
      </MDBox>
      */
    ),
  }));

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
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
                  Schedules Table
                </MDTypography>
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
                  Add Schedule
                </MDTypography>
              </MDBox>
              <MDBox pt={3}>
                <Card>
                  <MDBox pt={4} pb={3} px={3}>
                    <MDBox component="form" onSubmit={handleSubmit} role="form">
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Start Time"
                          fullWidth
                          name="startTime"
                          value={schedule.startTime}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="End Time"
                          fullWidth
                          name="endTime"
                          value={schedule.endTime}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Classroom"
                          fullWidth
                          name="classroom"
                          value={schedule.classroom}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Days"
                          fullWidth
                          name="days"
                          value={schedule.days}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Course"
                          fullWidth
                          name="course"
                          value={schedule.course}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Subject"
                          fullWidth
                          name="subject"
                          value={schedule.subject}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Teacher"
                          fullWidth
                          name="userCourse"
                          value={schedule.userCourse}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mt={4} mb={1}>
                        <MDButton variant="gradient" color="info" fullWidth type="submit">
                          Create Schedule
                        </MDButton>
                      </MDBox>
                    </MDBox>
                  </MDBox>
                </Card>
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
};

export default ScheduleTable;
