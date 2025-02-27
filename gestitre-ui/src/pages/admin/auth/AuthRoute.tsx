import {Navigate, Route, Routes} from "react-router-dom";
import NotFound from "@/pages/NotFound.tsx";
import LoginForm from "@/pages/admin/auth/LoginForm.tsx";
import VerifyCodeForm from "@/pages/admin/auth/VerifyCodeForm.tsx";

const AuthRoute = () => {
    return (
        <Routes>
            <Route>
                <Route index path="/"  element={<Navigate to="login"/>}/>
                <Route path="login"  element={<LoginForm/>}/>
                <Route path="verify-code"  element={<VerifyCodeForm/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    );
};

export default AuthRoute;