import React, { useState } from "react";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDInput from "components/MDInput";
import MDButton from "components/MDButton";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import axiosInstance from "../../services/axios";
import { Card, Grid } from "@mui/material";

const CourseManagement = () => {
  const [newCourse, setNewCourse] = useState({ name: "", capacity: "" });
  const [searchName, setSearchName] = useState("");
  const [courseData, setCourseData] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");

  // Manejar cambios en el formulario para crear un curso
  const handleNewCourseChange = (e) => {
    const { name, value } = e.target;
    setNewCourse((prev) => ({ ...prev, [name]: value }));
  };

  // Crear un curso
  const handleCreateCourse = async () => {
    try {
      await axiosInstance.post("/course", newCourse);
      alert("Course created successfully");
      setNewCourse({ name: "", capacity: "" });
    } catch (error) {
      alert("Error creating course: " + error.response.data);
    }
  };

  // Buscar un curso por nombre
  const handleSearchCourse = async () => {
    try {
      const response = await axiosInstance.get(`/course/${searchName}`);
      setCourseData(response.data);
      setErrorMessage("");
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setErrorMessage("Course not found");
        setCourseData(null);
      } else {
        setErrorMessage("Error fetching course: " + error.response.data);
      }
    }
  };

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          {/* Crear un curso */}
          <Grid item xs={12} md={6}>
            <Card>
              <MDBox p={3}>
                <MDTypography variant="h6">Create a New Course</MDTypography>
                <MDBox display="flex" flexDirection="column" gap={2} mt={2}>
                  <MDInput
                    type="text"
                    label="Course Name"
                    name="name"
                    value={newCourse.name}
                    onChange={handleNewCourseChange}
                    fullWidth
                  />
                  <MDInput
                    type="number"
                    label="Capacity"
                    name="capacity"
                    value={newCourse.capacity}
                    onChange={handleNewCourseChange}
                    fullWidth
                  />
                  <MDButton variant="gradient" color="success" onClick={handleCreateCourse}>
                    Create Course
                  </MDButton>
                </MDBox>
              </MDBox>
            </Card>
          </Grid>

          {/* Buscar un curso */}
          <Grid item xs={12} md={6}>
            <Card>
              <MDBox p={3}>
                <MDTypography variant="h6">Search for a Course</MDTypography>
                <MDBox display="flex" flexDirection="column" gap={2} mt={2}>
                  <MDInput
                    type="text"
                    label="Course Name"
                    value={searchName}
                    onChange={(e) => setSearchName(e.target.value)}
                    fullWidth
                  />
                  <MDButton variant="gradient" color="info" onClick={handleSearchCourse}>
                    Search
                  </MDButton>
                </MDBox>

                {/* Mostrar resultados */}
                {courseData && (
                  <MDBox mt={3}>
                    <MDTypography variant="body1">
                      <strong>ID:</strong> {courseData.id}
                    </MDTypography>
                    <MDTypography variant="body1">
                      <strong>Name:</strong> {courseData.name}
                    </MDTypography>
                    <MDTypography variant="body1">
                      <strong>Capacity:</strong> {courseData.capacity}
                    </MDTypography>
                  </MDBox>
                )}

                {/* Mostrar mensaje de error */}
                {errorMessage && (
                  <MDBox mt={3}>
                    <MDTypography variant="body1" color="error">
                      {errorMessage}
                    </MDTypography>
                  </MDBox>
                )}
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
};

export default CourseManagement;
