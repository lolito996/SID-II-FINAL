import React, { useEffect, useState } from "react";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import DataTable from "examples/Tables/DataTable";
import MDBadge from "components/MDBadge";
import axiosInstance from "../../services/axios";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import { Card, Grid } from "@mui/material";
import Footer from "examples/Footer";
import MDInput from "components/MDInput";
import MDButton from "components/MDButton";

// Aquí definimos el componente SubjectsTable
const SubjectsTable = () => {
  const [subjects, setSubjects] = useState([]);

  // Fetch subjects from the API
  useEffect(() => {
    const fetchSubjects = async () => {
      try {
        const response = await axiosInstance.get("/subject/subjects");
        console.log(response);
        setSubjects(response.data);
      } catch (error) {
        console.error("Error fetching subjects:", error);
      }
    };

    fetchSubjects();
  }, []);

  const [subject, setSubject] = useState({
    name: "",
    description: "",
    minimumPassingGrade: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setSubject({
      ...subject,
      [name]: value,
    });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosInstance.post("/subject/", subject);
      alert(response.data); // Muestra un mensaje de éxito o error
    } catch (error) {
      alert("Error creating subject: " + error.message); // Muestra error si falla
    }
  };

  // Función para eliminar la materia
  const handleDelete = async (id) => {
    try {
      await axiosInstance.delete("/subject/", {
        data: { id },
      });
      // Eliminar la materia del estado
      setSubjects((prevSubjects) => prevSubjects.filter((subject) => subject.id !== id));
    } catch (error) {
      alert("Error deleting subject: " + error.message); // Muestra error si falla
      console.error("Error deleting subject:", error);
    }
  };

  const columns = [
    { Header: "ID", accessor: "id", align: "left" },
    { Header: "Name", accessor: "name", align: "left" },
    { Header: "Description", accessor: "description", align: "left" },
    { Header: "Minimum Passing Grade", accessor: "minimumPassingGrade", align: "center" },
    { Header: "Status", accessor: "status", align: "center" },
    { Header: "Action", accessor: "action", align: "center" },
  ];

  const rows = subjects.map((subject) => ({
    id: subject.id,
    name: subject.name,
    description: subject.description,
    minimumPassingGrade: subject.minimumPassingGrade,
    status: (
      <MDBox ml={-1}>
        <MDBadge badgeContent="active" color="success" variant="gradient" size="sm" />
      </MDBox>
    ),
    action: (
      <MDTypography
        component="a"
        href="#"
        variant="caption"
        color="text"
        fontWeight="medium"
        onClick={() => handleDelete(subject.id)} // Llamada a la función de eliminación
        sx={{ cursor: "pointer", color: "red" }}
      >
        Delete
      </MDTypography>
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
                  Subjects Table
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
                  Add Subject
                </MDTypography>
              </MDBox>
              <MDBox pt={3}>
                <Card>
                  <MDBox pt={4} pb={3} px={3}>
                    <MDBox component="form" onSubmit={handleSubmit} role="form">
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Name"
                          fullWidth
                          name="name"
                          value={subject.name}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="text"
                          label="Description"
                          fullWidth
                          name="description"
                          value={subject.description}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mb={2}>
                        <MDInput
                          type="number"
                          label="Minimum Passing Grade"
                          fullWidth
                          name="minimumPassingGrade"
                          value={subject.minimumPassingGrade}
                          onChange={handleChange}
                        />
                      </MDBox>
                      <MDBox mt={4} mb={1}>
                        <MDButton variant="gradient" color="info" fullWidth type="submit">
                          Create Subject
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

export default SubjectsTable;
