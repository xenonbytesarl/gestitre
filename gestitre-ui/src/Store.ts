import {configureStore} from "@reduxjs/toolkit";
import navbarSlice from "@/layout/navbar/NavbarSlice.ts";
import sidebarSlice from "@/layout/sidebar/SidebarSlice.tsx";

export const store = configureStore({
    reducer: {
        navbar: navbarSlice,
        sidebar: sidebarSlice
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type RootDispatch = typeof store.dispatch;