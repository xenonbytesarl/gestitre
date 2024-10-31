import {configureStore} from "@reduxjs/toolkit";
import navbarSlice from "@/layout/navbar/NavbarSlice.ts";

export const store = configureStore({
    reducer: {
        navbar: navbarSlice
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type RootDispatch = typeof store.dispatch;