import React, { useEffect, useState } from "react";
import { Grid, Card, Box, Typography, Button, TextField, MenuItem } from "@mui/material";
import DataTable from "examples/Tables/DataTable"; // Asumiendo que usas un componente de tabla reutilizable
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import axiosInstance from "../../services/axios";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDInput from "components/MDInput";
import MDButton from "components/MDButton";
import { actions } from "react-table";
import MDBadge from "components/MDBadge";

const RoleManagement = () => {
  const [roles, setRoles] = useState([]);
  const [permissions, setPermissions] = useState([]);

  const fetchRolesPermissions = async () => {
    console.log("fetchRolesPermissions");
    const response = await axiosInstance.get("/role/rolesPermissions");
    setRoles(response.data.roles);
    setPermissions(response.data.permissions);
  };
  useEffect(() => {
    fetchRolesPermissions();
  }, []);

  const [newRole, setNewRole] = useState("");
  const [newRoleDescription, setNewRoleDescription] = useState("");
  const [selectedRole, setSelectedRole] = useState("");
  const [selectedPermission, setSelectedPermission] = useState("");

  const columns = [
    { accessor: "id", Header: "ID", width: 100 },
    { accessor: "name", Header: "Nombre del Rol", width: 200 },
    { accessor: "permissions", Header: "Permisos", width: 300 },
    {
      accessor: "actions",
      Header: "Acciones",
      renderCell: (params) => (
        <Button variant="contained" color="error" onClick={() => handleDeleteRole(params.row.id)}>
          Eliminar
        </Button>
      ),
    },
  ];

  const rows = roles.map((role) => ({
    id: role.id,
    name: role.name,
    permissions: (
      <MDBox sx={{ display: "inline-flex", gap: 1, flexWrap: "wrap", width: 400 }}>
        {role.permissions.map((rp, idx) => (
          <MDBadge
            badgeContent={permissions.find((p) => p.id == rp).name}
            size="sm"
            variant="gradient"
            color="secondary"
            key={idx}
          />
        ))}
      </MDBox>
    ),
    actions: (
      <MDTypography
        component="a"
        href="#"
        variant="caption"
        color="text"
        fontWeight="medium"
        onClick={() => handleDeleteRole(role.id)} // Llamada a la función de eliminación
        sx={{ cursor: "pointer", color: "red" }}
      >
        Delete
      </MDTypography>
    ),
  }));

  const handleSubmitNewRole = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosInstance.post("/role/roles/create", {
        name: newRole,
        description: newRoleDescription, // Si es necesario
      });
      console.log("Response: ", response);
      setRoles([...roles, { id: response.data.id, name: newRole, permissions: [] }]); // Actualiza el estado
      setNewRole("");
      setNewRoleDescription(""); // Limpia los campos
    } catch (error) {
      console.error("Error al crear el rol:", error.response?.data?.error || error.message);
    }
  };

  const handleDeleteRole = async (roleId) => {
    try {
      const response = await axiosInstance.delete(`/role/roles/delete/${roleId}`);
      console.log(response.data.message);
      setRoles(roles.filter((role) => role.id !== roleId)); // Elimina el rol del estado
    } catch (error) {
      console.error("Error al eliminar el rol:", error.response?.data?.error || error.message);
    }
  };

  const handleAssignPermission = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosInstance.post("/role/assign-permission", {
        roleId: selectedRole,
        permissionId: selectedPermission,
      });
      if (response.status === 200) {
        console.log(response);
        fetchRolesPermissions();
      }
    } catch (error) {
      console.error("Error al asignar permiso:", error);
      alert("Hubo un error al asignar el permiso. Por favor, inténtalo de nuevo.");
    }
  };

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          {/* Tabla para listar roles */}
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
                  Gestión de Roles
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

          {/* Formulario para agregar un nuevo rol */}
          <Grid item xs={12} md={6}>
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
                  Crear Rol
                </MDTypography>
              </MDBox>
              <MDBox py={3} px={3}>
                <form onSubmit={handleSubmitNewRole}>
                  <MDBox mb={2}>
                    <MDInput
                      type="text"
                      label="Nombre del Rol"
                      fullWidth
                      value={newRole}
                      onChange={(e) => setNewRole(e.target.value)}
                      required
                    />
                  </MDBox>
                  <MDBox mb={2}>
                    <MDInput
                      type="text"
                      label="Descripción del Rol"
                      fullWidth
                      value={newRoleDescription}
                      onChange={(e) => setNewRoleDescription(e.target.value)}
                      required
                    />
                  </MDBox>
                  <MDBox mt={2}>
                    <MDButton variant="contained" color="info" type="submit" fullWidth>
                      Guardar Rol
                    </MDButton>
                  </MDBox>
                </form>
              </MDBox>
            </Card>
          </Grid>

          {/* Formulario para asociar permisos a roles */}
          <Grid item xs={12} md={6}>
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
                  Asociar Permiso a Rol
                </MDTypography>
              </MDBox>
              <MDBox py={3} px={3}>
                <form onSubmit={handleAssignPermission}>
                  <MDBox mb={2}>
                    <MDInput
                      select
                      label="Selecciona un Rol"
                      fullWidth
                      value={selectedRole}
                      onChange={(e) => setSelectedRole(e.target.value)}
                      required
                      SelectProps={{
                        native: true, // Para un dropdown estilo HTML
                      }}
                      InputLabelProps={{
                        shrink: true, // Elimina el problema de superposición
                      }}
                    >
                      <option value="">Seleccionar</option>
                      {roles.map((role) => (
                        <option key={role.id} value={role.id}>
                          {role.name}
                        </option>
                      ))}
                    </MDInput>
                  </MDBox>
                  <MDBox mb={2}>
                    <MDInput
                      select
                      label="Selecciona un Permiso"
                      fullWidth
                      value={selectedPermission}
                      onChange={(e) => setSelectedPermission(e.target.value)}
                      required
                      SelectProps={{
                        native: true, // Para un dropdown estilo HTML
                      }}
                      InputLabelProps={{
                        shrink: true, // Elimina el problema de superposición
                      }}
                    >
                      <option value="">Seleccionar</option>
                      {permissions.map((perm) => (
                        <option key={perm.id} value={perm.id}>
                          {perm.name}
                        </option>
                      ))}
                    </MDInput>
                  </MDBox>
                  <MDBox mt={2}>
                    <MDButton variant="contained" color="info" type="submit" fullWidth>
                      Asignar Permiso
                    </MDButton>
                  </MDBox>
                </form>
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
};

export default RoleManagement;
