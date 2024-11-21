import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import PropTypes from "prop-types";
import { Home } from "../pages/home/Home";
import SubjectsTable from "layouts/subjects";
const Authenticator = ({ preAuth, children }) => {
  const [auth, setAuth] = useState(false);
  const nav = useNavigate();
  useEffect(() => {
    const token = localStorage.getItem("token");
    let isAuth = false;
    console.log({ token });
    if (token) {
      const decodedData = jwtDecode(token);
      const now = Date.now();
      const notExpired = decodedData.exp * 1000 > now;
      if (notExpired) {
        isAuth = true;
      }
    }
    setAuth(isAuth);
    if (!isAuth) {
      localStorage.setItem("redirectUrl", window.location.href);
      window.location.href = "http://localhost:8080/Edutrack/login";
    }
  }, [preAuth, nav]);

  return <> {auth ? <SubjectsTable /> : null} </>;
};

Authenticator.propTypes = {
  preAuth: PropTypes.bool,
  children: PropTypes.node,
};

export default Authenticator;
