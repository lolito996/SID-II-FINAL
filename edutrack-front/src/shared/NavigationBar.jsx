import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

const Navbar = () => {
    return (
    <AppBar position="static" color="primary">
        <Toolbar>
        <IconButton
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
        >
            <MenuIcon />
        </IconButton>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
           Edutrack
        </Typography>
        <Button color="inherit">Inicio</Button>
        <Button color="inherit">Acerca</Button>
        <Button color="inherit">Contacto</Button>
        </Toolbar>
    </AppBar>
    );
}

export default Navbar;
