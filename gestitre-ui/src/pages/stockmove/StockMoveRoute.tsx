import NotFound from "@/pages/NotFound.tsx";
import {Navigate, Route, Routes} from "react-router-dom";
import StockMoveForm from "./StockMoveForm";
import StockMoveCard from "./StockMoveCard";
import StockMoveTree from "@/pages/stockmove/StockMoveTree.tsx";

const StockMoveRoute = () => {
    return (
        <Routes>
            <Route>
                <Route index path="/"  element={<Navigate to="tree"/>}/>
                <Route path="tree"  element={<StockMoveTree/>}/>
                <Route path="card"  element={<StockMoveCard/>}/>
                <Route path="form/new"  element={<StockMoveForm/>}/>
                <Route path="form/details/:stockMoveId"  element={<StockMoveForm/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Route>
        </Routes>
    );
};

export default StockMoveRoute;