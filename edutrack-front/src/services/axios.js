import axios from "axios";

// // Crear una instancia de axios
// const axiosInstance = axios.create({
//   baseURL: process.env.REACT_APP_API,
//   timeout: 10000,
//   headers: { "Content-Type": "application/json" },
// });

// export default axiosInstance;

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API,
  timeout: 10000,
  headers: {
    Accept: "application/json",
  },
  validateStatus: function validateStatus(status) {
    if (status >= 200 && status < 300) {
      return true;
    } else {
      return false;
    }
  },
  withCredentials: false,
});

// Interceptor para agregar el token a cada solicitud
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("access_token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;
