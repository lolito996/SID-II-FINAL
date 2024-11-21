/**
=========================================================
* Material Dashboard 2 React - v2.2.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*/

/** 
  All of the routes for the Material Dashboard 2 React are added here,
  You can add a new route, customize the routes and delete the routes here.

  Once you add a new route on this file it will be visible automatically on
  the Sidenav.

  For adding a new route you can follow the existing routes in the routes array.
  1. The `type` key with the `collapse` value is used for a route.
  2. The `type` key with the `title` value is used for a title inside the Sidenav. 
  3. The `type` key with the `divider` value is used for a divider between Sidenav items.
  4. The `name` key is used for the name of the route on the Sidenav.
  5. The `key` key is used for the key of the route (It will help you with the key prop inside a loop).
  6. The `icon` key is used for the icon of the route on the Sidenav, you have to add a node.
  7. The `collapse` key is used for making a collapsible item on the Sidenav that has other routes
  inside (nested routes), you need to pass the nested routes inside an array as a value for the `collapse` key.
  8. The `route` key is used to store the route location which is used for the react router.
  9. The `href` key is used to store the external links location.
  10. The `title` key is only for the item with the type of `title` and its used for the title text on the Sidenav.
  10. The `component` key is used to store the component of its route.
*/

// Material Dashboard 2 React layouts
import Dashboard from "layouts/dashboard";
import SubjectsTable from "layouts/subjects";
import SignIn from "layouts/authentication/sign-in";
import SignUp from "layouts/authentication/sign-up";
/*
import Tables from "layouts/tables";
import Billing from "layouts/billing";
import RTL from "layouts/rtl";
import Notifications from "layouts/notifications";
import Profile from "layouts/profile";
*/

// @mui icons
import Icon from "@mui/material/Icon";
import ScheduleTable from "layouts/schedule";
import RoleManagement from "layouts/role";
import PermissionsTable from "layouts/permissions";
import CourseManagement from "layouts/courses";
import AdminDashboard from "layouts/admin";
import GradesView from "layouts/grades";
import Authenticator from "../components/Authenticator";

const routes = [
  {
    type: "collapse",
    name: "Subjects", // Nuevo nombre para la sección de sujetos
    key: "subjects",
    icon: <Icon fontSize="small">book</Icon>, // Puedes usar cualquier ícono relacionado con los sujetos
    route: "/subjects", // Nueva ruta para los sujetos
    component: <SubjectsTable />, // El componente que muestra los sujetos
  },
  {
    type: "collapse",
    name: "Schedules", // Nuevo nombre para la sección de sujetos
    key: "schedules",
    icon: <Icon fontSize="small">school</Icon>, // Puedes usar cualquier ícono relacionado con los sujetos
    route: "/schedules", // Nueva ruta para los sujetos
    component: <ScheduleTable />, // El componente que muestra los sujetos
  },
  {
    type: "collapse",
    name: "Roles", // Nombre de la sección
    key: "roles",
    icon: <Icon fontSize="small">group</Icon>, // Icono relacionado con roles
    route: "/roles", // Nueva ruta para roles
    component: <RoleManagement />, // Componente que muestra la gestión de roles
  },
  {
    type: "collapse",
    name: "permissions",
    key: "permissions",
    icon: <Icon fontSize="small">lock</Icon>,
    route: "/permissions",
    component: <PermissionsTable />,
  },
  {
    type: "collapse",
    name: "courses",
    key: "courses",
    icon: <Icon fontSize="small">class</Icon>,
    route: "/courses",
    component: <CourseManagement />,
  },
  {
    type: "collapse",
    name: "admin",
    key: "admin",
    icon: <Icon fontSize="small">admin_panel_settings</Icon>,
    route: "/admin",
    component: <AdminDashboard />,
  },
  {
    type: "collapse",
    name: "Sign In",
    key: "sign-in",
    icon: <Icon fontSize="small">login</Icon>,
    route: "/authentication/sign-in",
    component: <SignIn />,
  },
  {
    type: "collapse",
    name: "grades",
    key: "grades",
    icon: <Icon fontSize="small">grading</Icon>,
    route: "/grades",
    component: <GradesView />,
  },
  /*
  {
    type: "collapse",
    name: "Dashboard",
    key: "dashboard",
    icon: <Icon fontSize="small">dashboard</Icon>,
    route: "/dashboard",
    component: <Dashboard />,
  },
  {
    type: "collapse",
    name: "Sign Up",
    key: "sign-up",
    icon: <Icon fontSize="small">assignment</Icon>,
    route: "/authentication/sign-up",
    component: <SignUp />,
  },
  {
    type: "collapse",
    name: "Dashboard",
    key: "dashboard",
    icon: <Icon fontSize="small">dashboard</Icon>,
    route: "/dashboard",
    component: <Dashboard />,
  },
  {
    type: "collapse",
    name: "Tables",
    key: "tables",
    icon: <Icon fontSize="small">table_view</Icon>,
    route: "/tables",
    component: <Tables />,
  },
  {
    type: "collapse",
    name: "Billing",
    key: "billing",
    icon: <Icon fontSize="small">receipt_long</Icon>,
    route: "/billing",
    component: <Billing />,
  },
  {
    type: "collapse",
    name: "RTL",
    key: "rtl",
    icon: <Icon fontSize="small">format_textdirection_r_to_l</Icon>,
    route: "/rtl",
    component: <RTL />,
  },
  {
    type: "collapse",
    name: "Notifications",
    key: "notifications",
    icon: <Icon fontSize="small">notifications</Icon>,
    route: "/notifications",
    component: <Notifications />,
  },
  {
    type: "collapse",
    name: "Profile",
    key: "profile",
    icon: <Icon fontSize="small">person</Icon>,
    route: "/profile",
    component: <Profile />,
  },
  */
];

export default routes;
