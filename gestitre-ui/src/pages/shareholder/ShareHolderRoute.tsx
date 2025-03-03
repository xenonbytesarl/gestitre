import {Navigate, Route, Routes} from "react-router-dom";
import NotFound from "@/pages/NotFound.tsx";
import ShareHolderTree from "@/pages/shareholder/ShareHolderTree.tsx";
import ShareHolderForm from "@/pages/shareholder/ShareHolderForm.tsx";
import ShareHolderCard from "@/pages/shareholder/ShareHolderCard.tsx";

const ShareHolderRoute = () => {
    return (
        <Routes>
            <Route>
                <Route index path="/"  element={<Navigate to="tree"/>}/>
                <Route path="tree"  element={<ShareHolderTree/>}/>
                <Route path="card"  element={<ShareHolderCard/>}/>
                <Route path="form/new"  element={<ShareHolderForm/>}/>
                <Route path="form/details/:shareholderId"  element={<ShareHolderForm/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    );
};

export default ShareHolderRoute;