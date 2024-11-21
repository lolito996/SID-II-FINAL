import React, { useEffect, useState } from "react";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import DataTable from "examples/Tables/DataTable";
import MDBadge from "components/MDBadge";
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
import Footer from "examples/Footer";
import MDButton from "components/MDButton";
import MDInput from "components/MDInput";

const UsersTable = () => {
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);
  const [selectedUserId, setSelectedUserId] = useState("");
  const [selectedRoleId, setSelectedRoleId] = useState("");
  const [newUser, setNewUser] = useState({
    username: "",
    password: "",
    name: "",
    identificationNumber: "",
    birthdate: "",
  });

  // Fetch usuarios y roles de la API
  useEffect(() => {
    const fetchUsersAndRoles = async () => {
      try {
        axiosInstance
          .get("/admin/users")
          .then((usersResponse) => {
            console.log(usersResponse.data);
            setUsers(usersResponse.data);
          })
          .catch((error) => {
            console.log("Error al tratar de traer usuarios :", error);
            // Maneja el error aquí, por ejemplo, mostrando un mensaje
          });

        const rolesResponse = await axiosInstance.get("role/rolesPermissions");
        setRoles(rolesResponse.data.roles);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchUsersAndRoles();
  }, []);

  // Generar las filas de la tabla con los roles como MDBadge
  const columns = [
    { Header: "ID", accessor: "id", align: "left" },
    { Header: "Username", accessor: "username", align: "left" },
    { Header: "Name", accessor: "name", align: "left" },
    { Header: "Roles", accessor: "roles", align: "left" },
  ];

  const rows = users.map((user) => ({
    id: user.id,
    username: user.username,
    name: user.name,
    roles: (
      <MDBox display="flex" gap={1}>
        {user.roles &&
          user.roles.map((role) => (
            <MDBadge key={role.id} color="info" badgeContent={role.name} variant="gradient" />
          ))}
      </MDBox>
    ),
  }));

  // Función para manejar la adición de un nuevo usuario
  const handleAddUser = async () => {
    try {
      const response = await axiosInstance.post("/admin/users", newUser);
      alert(response.data);
      setUsers([...users, { username: newUser.username, name: newUser.name }]);
      setNewUser({
        username: "",
        password: "",
        name: "",
        identificationNumber: "",
        birthdate: "",
      });
    } catch (error) {
      console.error("Error adding user:", error);
      alert(error.response.data);
    }
  };

  // Función para asignar un rol a un usuario
  const handleAssignRole = async () => {
    try {
      const response = await axiosInstance.post(`/role/${selectedUserId}/roles/${selectedRoleId}`);
      alert(response.data);
      // Actualizar los roles del usuario después de asignar el rol
      setUsers(
        users.map((user) =>
          user.id === selectedUserId
            ? {
                ...user,
                roles: [
                  ...user.roles,
                  {
                    id: selectedRoleId,
                    name: roles.find((role) => role.id === selectedRoleId).name,
                  },
                ],
              }
            : user
        )
      );
    } catch (error) {
      console.error("Error assigning role:", error);
      alert(error.response.data);
    }
  };

  // Función para eliminar un rol de un usuario
  const handleDeleteRole = async () => {
    try {
      const response = await axiosInstance.delete(
        `/role/${selectedUserId}/roles/${selectedRoleId}`
      );
      alert(response.data);
      // Actualizar los roles del usuario después de eliminar el rol
      setUsers(
        users.map((user) =>
          user.id === selectedUserId
            ? { ...user, roles: user.roles.filter((role) => role.id !== selectedRoleId) }
            : user
        )
      );
    } catch (error) {
      console.error("Error deleting role:", error);
      alert(error.response.data);
    }
  };

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
                  Users Table
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

          {/* Formulario para agregar un nuevo usuario */}
          <Grid item xs={12} md={6}>
            <Card>
              <MDBox mx={3} my={2}>
                <MDTypography variant="h6" gutterBottom>
                  Añadir Usuario
                </MDTypography>

                {/* Campos para agregar usuario */}
                <TextField
                  fullWidth
                  label="Name"
                  variant="outlined"
                  value={newUser.name}
                  onChange={(e) => setNewUser({ ...newUser, name: e.target.value })}
                  margin="normal"
                />
                <TextField
                  fullWidth
                  label="Username"
                  variant="outlined"
                  value={newUser.username}
                  onChange={(e) => setNewUser({ ...newUser, username: e.target.value })}
                  margin="normal"
                />
                <TextField
                  fullWidth
                  label="Password"
                  type="password"
                  variant="outlined"
                  value={newUser.password}
                  onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
                  margin="normal"
                />
                <TextField
                  fullWidth
                  label="Identification Number"
                  variant="outlined"
                  value={newUser.identificationNumber}
                  onChange={(e) => setNewUser({ ...newUser, identificationNumber: e.target.value })}
                  margin="normal"
                />
                <TextField
                  fullWidth
                  label="Birthdate"
                  type="date"
                  value={newUser.birthdate}
                  onChange={(e) => setNewUser({ ...newUser, birthdate: e.target.value })}
                  margin="normal"
                  InputLabelProps={{ shrink: true }}
                />

                <MDButton variant="gradient" color="info" onClick={handleAddUser} sx={{ mt: 2 }}>
                  Add User
                </MDButton>
              </MDBox>
            </Card>
          </Grid>

          {/* Formulario para asignar un rol */}
          <Grid item xs={12} md={6}>
            <Card>
              <MDBox mx={3} my={2} spacing={3}>
                <MDTypography variant="h6" gutterBottom>
                  Asignar/Eliminar rol a usuario
                </MDTypography>
                <MDBox my={2}>
                  <MDInput
                    select
                    label="Seleccionar usuario"
                    fullWidth
                    value={selectedUserId}
                    onChange={(e) => {
                      setSelectedUserId(e.target.value);
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
                    {users.map(
                      (user) => (
                        console.log(user.id),
                        (
                          <option key={user.id} value={user.id}>
                            {user.name}
                          </option>
                        )
                      )
                    )}
                  </MDInput>
                </MDBox>
                <MDBox my={2}>
                  <MDInput
                    select
                    label="Seleccionar rol"
                    fullWidth
                    value={selectedRoleId}
                    onChange={(e) => {
                      setSelectedRoleId(e.target.value);
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
                    {roles.map(
                      (role) => (
                        console.log(role.id),
                        (
                          <option key={role.id} value={role.id}>
                            {role.name}
                          </option>
                        )
                      )
                    )}
                  </MDInput>
                </MDBox>
                <MDBox display="flex" justifyContent="center" gap={3} alignItems="center">
                  <MDButton
                    variant="gradient"
                    color="info"
                    onClick={handleAssignRole}
                    sx={{ mt: 2 }}
                  >
                    Assign Role
                  </MDButton>
                  <MDButton
                    variant="gradient"
                    color="error"
                    onClick={handleDeleteRole}
                    sx={{ mt: 2 }}
                  >
                    Remove Role
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

export default UsersTable;
