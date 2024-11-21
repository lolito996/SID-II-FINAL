import { createBrowserRouter, createRoutesFromElements, Route } from "react-router-dom"
import { Home } from "../pages/home/Home";
import Header from "../components/Header";
import Authenticator from "../components/Authenticator";

const routes = createRoutesFromElements([
    <Route path="/" element={<Header/>}>
    <Route path="/home" element={
        <Authenticator>
            <Home />
        </Authenticator>
    } />,
    <Route path="/subjects" element={<SubjectsTable/>}/>
    <Route path="/schedule" element={<ScheduleTable/>}/>
    <Route path="/course" element={<CourseManagement/>}/>
    <Route path="/grades" element={<GradesView/>}/>
</Route>
]);

export const router = createBrowserRouter(routes)