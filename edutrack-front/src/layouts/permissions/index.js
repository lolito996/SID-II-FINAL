import React, { useEffect, useState } from "react";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import DataTable from "examples/Tables/DataTable";
import MDBadge from "components/MDBadge";
import axiosInstance from "../../services/axios";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import { Card, Grid, Switch } from "@mui/material";
import Footer from "examples/Footer";
import MDInput from "components/MDInput";
import MDButton from "components/MDButton";

const PermissionsTable = () => {
  const [permissions, setPermissions] = useState([]);
  const [editing, setEditing] = useState({}); // Estado para rastrear qué permiso está siendo editado
  const [newPermission, setNewPermission] = useState({ name: "", description: "" }); // Estado para añadir permisos

  // Fetch permisos de la API
  useEffect(() => {
    const fetchPermissions = async () => {
      try {
        const response = await axiosInstance.get("/permissions");
        setPermissions(response.data);
      } catch (error) {
        console.error("Error fetching permissions:", error);
      }
    };

    fetchPermissions();
  }, []);

  // Alternar modo de edición
  const toggleEdit = (id) => {
    setEditing((prevEditing) => ({
      ...prevEditing,
      [id]: !prevEditing[id],
    }));
  };

  // Manejar cambios en los campos editados
  const handleEditChange = (id, e) => {
    const { name, value } = e.target;
    setPermissions((prevPermissions) =>
      prevPermissions.map((permission) =>
        permission.id === id ? { ...permission, [name]: value } : permission
      )
    );
  };

  // Enviar cambios de un permiso editado
  const handleEditSubmit = async (id) => {
    const permission = permissions.find((p) => p.id === id);
    try {
      await axiosInstance.put(`/permission/${id}`, {
        name: permission.name,
        description: permission.description,
      });
      alert("Permission updated successfully");
      toggleEdit(id); // Salir del modo edición tras guardar
    } catch (error) {
      alert("Error updating permission: " + error.response.data);
      console.error("Error updating permission:", error);
    }
  };

  // Manejar cambios en los campos del nuevo permiso
  const handleNewPermissionChange = (e) => {
    const { name, value } = e.target;
    setNewPermission((prev) => ({ ...prev, [name]: value }));
  };

  // Añadir un nuevo permiso
  const handleAddPermission = async () => {
    try {
      const response = await axiosInstance.post("/permission", newPermission);
      setPermissions((prevPermissions) => [...prevPermissions, newPermission]);
      setNewPermission({ name: "", description: "" });
      alert(response.data);
    } catch (error) {
      alert("Error adding permission: " + error.response.data);
      console.error("Error adding permission:", error);
    }
  };

  // Eliminar un permiso
  const handleDeletePermission = async (id) => {
    try {
      await axiosInstance.delete(`/permission/${id}`);
      setPermissions((prevPermissions) =>
        prevPermissions.filter((permission) => permission.id !== id)
      );
      alert("Permission deleted successfully");
    } catch (error) {
      alert("Error deleting permission: " + error.response.data);
      console.error("Error deleting permission:", error);
    }
  };

  const columns = [
    { Header: "ID", accessor: "id", align: "left" },
    { Header: "Name", accessor: "name", align: "left" },
    { Header: "Description", accessor: "description", align: "left" },
    { Header: "Action", accessor: "action", align: "center" },
  ];

  const rows = permissions.map((permission) => ({
    id: permission.id,
    name: editing[permission.id] ? (
      <MDInput
        type="text"
        value={permission.name}
        name="name"
        onChange={(e) => handleEditChange(permission.id, e)}
        fullWidth
      />
    ) : (
      permission.name
    ),
    description: editing[permission.id] ? (
      <MDInput
        type="text"
        value={permission.description}
        name="description"
        onChange={(e) => handleEditChange(permission.id, e)}
        fullWidth
      />
    ) : (
      permission.description
    ),
    action: (
      <MDBox display="flex" alignItems="center" justifyContent="center" gap={1}>
        <MDButton
          variant="gradient"
          color={editing[permission.id] ? "success" : "info"}
          size="small"
          onClick={() =>
            editing[permission.id] ? handleEditSubmit(permission.id) : toggleEdit(permission.id)
          }
        >
          {editing[permission.id] ? "Save" : "Edit"}
        </MDButton>
        <Switch
          checked={!!editing[permission.id]}
          onChange={() => toggleEdit(permission.id)}
          color="info"
        />
        <MDButton
          variant="gradient"
          color="error"
          size="small"
          onClick={() => handleDeletePermission(permission.id)}
        >
          Delete
        </MDButton>
      </MDBox>
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
                  Permissions Table
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

          {/* Añadir nuevo permiso */}
          <Grid item xs={12}>
            <Card>
              <MDBox p={3}>
                <MDTypography variant="h6">Add New Permission</MDTypography>
                <MDBox display="flex" gap={2} mt={2}>
                  <MDInput
                    type="text"
                    label="Name"
                    name="name"
                    value={newPermission.name}
                    onChange={handleNewPermissionChange}
                    fullWidth
                  />
                  <MDInput
                    type="text"
                    label="Description"
                    name="description"
                    value={newPermission.description}
                    onChange={handleNewPermissionChange}
                    fullWidth
                  />
                  <MDButton variant="gradient" color="success" onClick={handleAddPermission}>
                    Add
                  </MDButton>
                </MDBox>
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
};

export default PermissionsTable;
