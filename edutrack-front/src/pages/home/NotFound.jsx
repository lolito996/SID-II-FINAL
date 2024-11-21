import React from 'react';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';

// Constante para el mensaje de "Not Found"
const NOT = "Página no encontrada. La URL que buscas no existe.";

export const NotFound = () => {
  const navigate = useNavigate();

  return (
    <div style={{ textAlign: 'center', marginTop: '2rem' }}>
      <h1>404</h1>
      <p>{NOT}</p>
      <Button 
        variant="contained" 
        color="primary" 
        onClick={() => navigate('/')}
      >
        Volver al Inicio
      </Button>
    </div>
  );
};
