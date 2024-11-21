import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import { useNavigate } from "react-router-dom";

export const Home = () => {
  const nav = useNavigate();
  return (
    <div>
      {/* Navbar */}
      <AppBar position="static" color="primary">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Bienvenidos a Edutrack
          </Typography>
          <Button color="inherit" onClick={() => nav("/")}>
            Iniciar Sesión
          </Button>
        </Toolbar>
      </AppBar>

      {/* Contenido Principal */}
      <Container maxWidth="md" style={{ marginTop: "2rem" }}>
        <h1 style={{ textAlign: "center" }}>Edutrack</h1>
        <p style={{ textAlign: "center", fontSize: "1.2rem" }}>
          Bienvenido a EduApp, una plataforma educativa diseñada para estudiantes y profesores.
        </p>

        <Box display="flex" justifyContent="space-around" mt={4}>
          {/* Card para Estudiantes */}
          <div
            style={{
              border: "1px solid #ddd",
              padding: "1.5rem",
              width: "40%",
              textAlign: "center",
            }}
          >
            <h2>Estudiantes</h2>
            <p>Accede a tus cursos, tareas y recursos educativos en un solo lugar.</p>
            <Button variant="contained" color="primary">
              Ingresar como Estudiante
            </Button>
          </div>

          {/* Card para Profesores */}
          <div
            style={{
              border: "1px solid #ddd",
              padding: "1.5rem",
              width: "40%",
              textAlign: "center",
            }}
          >
            <h2>Profesores</h2>
            <p>Administra tus clases, crea contenidos y haz seguimiento a tus estudiantes.</p>
            <Button variant="contained" color="secondary">
              Ingresar como Profesor
            </Button>
          </div>
        </Box>
      </Container>

      {/* Footer */}
      <footer
        style={{
          textAlign: "center",
          padding: "1rem",
          marginTop: "2rem",
          backgroundColor: "#f5f5f5",
        }}
      >
        <Typography variant="body2" color="textSecondary">
          © 2024 EduApp. Todos los derechos reservados.
        </Typography>
      </footer>
    </div>
  );
};
