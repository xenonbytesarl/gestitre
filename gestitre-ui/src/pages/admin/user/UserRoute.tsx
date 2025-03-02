import {Navigate, Route, Routes} from "react-router-dom";
import NotFound from "@/pages/NotFound.tsx";
import UserTree from "@/pages/admin/user/UserTree.tsx";
import UserForm from "@/pages/admin/user/UserForm.tsx";


const UserRoute = () => {
    return (
        <Routes>
            <Route>
                <Route index path="/"  element={<Navigate to="tree"/>}/>
                <Route path="tree"  element={<UserTree/>}/>
                <Route path="form/new"  element={<UserForm/>}/>
                <Route path="form/details/:userId" element={<UserForm/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    );
};

export default UserRoute;