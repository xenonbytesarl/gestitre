import {combineSlices} from "@reduxjs/toolkit";
import authSlice from "@/pages/admin/auth/AuthSlice.ts";

const adminSlice = combineSlices({
    auth: authSlice
});

export default adminSlice