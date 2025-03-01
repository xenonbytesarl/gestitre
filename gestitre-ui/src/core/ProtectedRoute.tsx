import {useDispatch, useSelector} from "react-redux";
import {getIsAuthenticated, recoverAuthentication} from "@/pages/admin/auth/AuthSlice.ts";
import {Navigate, Outlet, useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import {RootDispatch} from "@/core/Store.ts";
import {persistLastVisitedUrl} from "@/shared/utils/localStorageUtils.ts";
import {REDIRECT} from "@/shared/constant/globalConstant.ts";


const ProtectedRoute = () => {
    const isAuthenticated = useSelector(getIsAuthenticated);
    const [loading, setLoading] = useState(true);
    const location = useLocation();

    const dispatch = useDispatch<RootDispatch>();

    useEffect(() => {
        dispatch(recoverAuthentication()).finally(() => setLoading(false));
    }, [dispatch]);

    if (loading) return <div>Loading...</div>;

    if (!isAuthenticated) {
        persistLastVisitedUrl(location.pathname);
        return <Navigate to={`/admin/auth/login?${REDIRECT}=${encodeURIComponent(location.pathname)}`} />;
    }
    return <Outlet/>;
}

export default ProtectedRoute;