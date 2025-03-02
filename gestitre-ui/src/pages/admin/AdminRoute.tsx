import {Navigate, Route, Routes} from "react-router-dom";
import NotFound from "@/pages/NotFound.tsx";
import AuthRoute from "@/pages/admin/auth/AuthRoute.tsx";
import UserRoute from "@/pages/admin/user/UserRoute.tsx";
import ProtectedRoute from "@/core/ProtectedRoute.tsx";
import Layout from "@/layout/Layout.tsx";

const AdminRoute = () => {
    return (
        <Routes>
            <Route>
                <Route index path="/"  element={<Navigate to="auth"/>}/>
                <Route path="auth/*"  element={<AuthRoute/>}/>
                <Route element={<ProtectedRoute/>}>
                    <Route path="/" element={<Layout/>}>
                        <Route path="users/*"  element={<UserRoute/>}/>
                    </Route>
                </Route>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    );
};

export default AdminRoute;