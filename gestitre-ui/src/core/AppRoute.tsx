import {Navigate, Route, Routes} from "react-router-dom";
import Layout from "@/layout/Layout.tsx";
import Dashboard from "@/pages/dashboard/Dashboard.tsx";
import NotFound from "@/pages/NotFound.tsx";
import CompanyRoute from "@/pages/company/CompanyRoute.tsx";
import AdminRoute from "@/pages/admin/AdminRoute.tsx";
import ProtectedRoute from "@/core/ProtectedRoute.tsx";
import ShareHolderRoute from "@/pages/shareholder/ShareHolderRoute.tsx";
import StockMoveRoute from "@/pages/stockmove/StockMoveRoute.tsx";


const AppRoute = () => {
    return (
        <Routes>
            <Route path="admin/*" element={<AdminRoute/>}/>
           <Route element={<ProtectedRoute/>}>
               <Route path="/" element={<Layout/>}>
                   <Route index path="/" element={<Navigate to="/dashboard"/>}/>
                   <Route path="dashboard" element={<Dashboard/>}/>
                   <Route path="companies/*" element={<CompanyRoute/>}/>
                   <Route path="shareholders/*" element={<ShareHolderRoute/>}/>
                   <Route path="stock-moves/*" element={<StockMoveRoute/>}/>
               </Route>
           </Route>
           <Route path="*" element={<NotFound/>}/>
        </Routes>
    )
}

export default AppRoute;