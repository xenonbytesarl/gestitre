import {Navigate, Route, Routes} from "react-router-dom";
import NotFound from "@/pages/NotFound.tsx";
import AuthRoute from "@/pages/admin/auth/AuthRoute.tsx";

const AdminRoute = () => {
    return (
        <Routes>
            <Route>
                <Route index path="/"  element={<Navigate to="auth"/>}/>
                <Route path="auth/*"  element={<AuthRoute/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    );
};

export default AdminRoute;