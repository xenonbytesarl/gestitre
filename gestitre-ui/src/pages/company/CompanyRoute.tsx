import {Navigate, Route, Routes} from "react-router-dom";
import NotFound from "@/pages/NotFound.tsx";
import CompanyTree from "@/pages/company/CompanyTree.tsx";
import CompanyForm from "@/pages/company/CompanyForm.tsx";
import CompanyCard from "@/pages/company/CompanyCard.tsx";

const CompanyRoute = () => {
    return (
        <Routes>
            <Route>
                <Route index path="/"  element={<Navigate to="tree"/>}/>
                <Route path="tree"  element={<CompanyTree/>}/>
                <Route path="card"  element={<CompanyCard/>}/>
                <Route path="form/new"  element={<CompanyForm/>}/>
                <Route path="form/details/:productId"  element={<CompanyForm/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    );
};

export default CompanyRoute;