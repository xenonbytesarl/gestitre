import {combineSlices} from "@reduxjs/toolkit";
import authSlice from "@/pages/admin/auth/AuthSlice.ts";

export const adminSlice = combineSlices({
    auth: authSlice
});