import {Navigate, Route, Routes} from "react-router-dom";
import Layout from "@/layout/Layout.tsx";
import Dashboard from "@/pages/dashboard/Dashboard.tsx";
import NotFound from "@/pages/NotFound.tsx";
import CompanyRoute from "@/pages/company/CompanyRoute.tsx";



const AppRoute = () => {
    return (
        <Routes>
            <Route path="/" element={<Layout/>}>
                <Route index path="/" element={<Navigate to="/dashboard"/>}/>
                <Route path="dashboard" element={<Dashboard/>}/>
                <Route path="companies/*" element={<CompanyRoute/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    )
}

export default AppRoute;