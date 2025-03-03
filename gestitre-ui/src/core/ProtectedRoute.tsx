import {useDispatch, useSelector} from "react-redux";
import {getIsAuthenticated, recoverAuthentication, recoverProfile} from "@/pages/admin/auth/AuthSlice.ts";
import {Navigate, Outlet, useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import {RootDispatch} from "@/core/Store.ts";
import {persistLastVisitedUrl} from "@/shared/utils/localStorageUtils.ts";
import {REDIRECT} from "@/shared/constant/globalConstant.ts";


const ProtectedRoute = () => {
    const isAuthenticated = useSelector(getIsAuthenticated);
    const [loadingAuth, setLoadingAuth] = useState(true);
    const [loadingProfile, setLoadingProfile] = useState(true);
    const location = useLocation();

    const dispatch = useDispatch<RootDispatch>();

    useEffect(() => {
        console.log(isAuthenticated)
        if(isAuthenticated) {
            dispatch(recoverAuthentication()).finally(() => setLoadingAuth(false));
            dispatch(recoverProfile()).finally(() => setLoadingProfile(false));
        }
    }, [dispatch]);

    if (loadingAuth) return <div>Loading...</div>;
    if (loadingProfile) return <div>Loading...</div>;

    if (!isAuthenticated) {
        persistLastVisitedUrl(location.pathname);
        return <Navigate to={`/admin/auth/login?${REDIRECT}=${encodeURIComponent(location.pathname)}`} />;
    }
    return <Outlet/>;
}

export default ProtectedRoute;