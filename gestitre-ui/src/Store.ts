import {configureStore} from "@reduxjs/toolkit";
import navbarSlice from "@/layout/navbar/NavbarSlice.ts";
import sidebarSlice from "@/layout/sidebar/SidebarSlice.tsx";
import CompanySlice from "@/pages/company/CompanySlice.ts";
import CertificateTemplateSlice from "@/pages/company/certificatetemplate/CertificateTemplateSlice.ts";

export const store = configureStore({
    reducer: {
        navbar: navbarSlice,
        sidebar: sidebarSlice,
        company: CompanySlice,
        certificateTemplate: CertificateTemplateSlice
    }
});

export type RootState = ReturnType<typeof store.getState>;
export type RootDispatch = typeof store.dispatch;