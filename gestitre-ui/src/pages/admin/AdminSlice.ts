import {combineSlices} from "@reduxjs/toolkit";
import authSlice from "@/pages/admin/auth/AuthSlice.ts";
import UserSlice from "@/pages/admin/user/UserSlice.ts";

const adminSlice = combineSlices({
    auth: authSlice,
    user: UserSlice
});

export default adminSlice