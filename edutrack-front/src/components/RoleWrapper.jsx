// RoleWrapper.js
import React from 'react';
import { useAuth } from './AuthContext';
import { Redirect } from 'react-router-dom';

const RoleWrapper = ({ children, allowedRoles }) => {
    const { user } = useAuth();

    if (!user || !allowedRoles.includes(user.role)) {
        return <Redirect to="/not-authorized" />;
    }

    return children;
};

export default RoleWrapper;