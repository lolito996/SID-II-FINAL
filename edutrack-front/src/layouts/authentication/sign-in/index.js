import { useState } from "react";
import { useNavigate } from "react-router-dom"; // Importar useNavigate para redirigir
import { useDispatch } from "react-redux";
import { loginSuccess } from "reducers/Actions";

// @mui material components
import Card from "@mui/material/Card";
import Grid from "@mui/material/Grid";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDInput from "components/MDInput";
import MDButton from "components/MDButton";

// Authentication layout components
import BasicLayout from "layouts/authentication/components/BasicLayout";

// Images
import bgImage from "assets/images/bg-sign-in-basic.jpeg";
import axiosInstance from "../../../services/axios";

function Basic() {
  const [username, setUsername] = useState(""); // Estado para el username
  const [password, setPassword] = useState(""); // Estado para el password
  const [rememberMe, setRememberMe] = useState(false);

  const navigate = useNavigate(); // hook para redirigir al usuario
  const dispatch = useDispatch();

  const handleSetRememberMe = () => setRememberMe(!rememberMe);

  const handleLogin = async (e) => {
    e.preventDefault(); // Prevenir el comportamiento por defecto del formulario

    try {
      const response = await axiosInstance
        .post("auth/login", {
          username: username,
          password: password,
        })
        .then((response) => {
          const { token, id, username, courseId, roles } = response.data;
          if (response.status === 200) {
            localStorage.setItem("access_token", token);
            dispatch(
              loginSuccess({
                id: id,
                username: username,
                courseId: courseId,
                roles: roles,
              })
            );
            navigate("/"); // Redirige a la ruta raíz
          }
        });
      // Si el inicio de sesión es exitoso, redirigir a la ruta raíz
    } catch (error) {
      console.error("Error de inicio de sesión:", error);
      // Maneja el error aquí, por ejemplo, mostrando un mensaje
      alert("Error al iniciar sesión: " + error);
    }
  };

  return (
    <BasicLayout image={bgImage}>
      <Card>
        <MDBox
          variant="gradient"
          bgColor="info"
          borderRadius="lg"
          coloredShadow="info"
          mx={2}
          mt={-3}
          p={2}
          mb={1}
          textAlign="center"
        >
          <MDTypography variant="h4" fontWeight="medium" color="white" mt={1}>
            Iniciar Sesión
          </MDTypography>
          <Grid container spacing={3} justifyContent="center" sx={{ mt: 1, mb: 2 }}></Grid>
        </MDBox>
        <MDBox pt={4} pb={3} px={3}>
          <MDBox component="form" role="form" onSubmit={handleLogin}>
            <MDBox mb={2}>
              <MDInput
                type="text"
                label="Username"
                fullWidth
                value={username} // Vincula el estado con el valor del input
                onChange={(e) => setUsername(e.target.value)} // Actualiza el estado
              />
            </MDBox>
            <MDBox mb={2}>
              <MDInput
                type="password"
                label="Password"
                fullWidth
                value={password} // Vincula el estado con el valor del input
                onChange={(e) => setPassword(e.target.value)} // Actualiza el estado
              />
            </MDBox>
            <MDBox display="flex" alignItems="center" ml={-1}></MDBox>
            <MDBox mt={4} mb={1}>
              <MDButton variant="gradient" color="info" fullWidth type="submit">
                Iniciar Sesión
              </MDButton>
            </MDBox>
            <MDBox mt={3} mb={1} textAlign="center"></MDBox>
          </MDBox>
        </MDBox>
      </Card>
    </BasicLayout>
  );
}

export default Basic;
