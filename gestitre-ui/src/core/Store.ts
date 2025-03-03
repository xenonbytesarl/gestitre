import {configureStore} from "@reduxjs/toolkit";
import navbarSlice from "@/layout/navbar/NavbarSlice.ts";
import sidebarSlice from "@/layout/sidebar/SidebarSlice.tsx";
import CompanySlice from "@/pages/company/CompanySlice.ts";
import CertificateTemplateSlice from "@/pages/company/certificatetemplate/CertificateTemplateSlice.ts";
import adminSlice from "@/pages/admin/AdminSlice.ts";
import ShareholderSlice from "@/pages/shareholder/ShareholderSlice.tsx";

const store = configureStore({
    reducer: {
        navbar: navbarSlice,
        sidebar: sidebarSlice,
        admin: adminSlice,
        company: CompanySlice,
        shareHolder: ShareholderSlice,
        certificateTemplate: CertificateTemplateSlice
    }/*,
    middleware: (getDefaultMiddleware) => {
        return getDefaultMiddleware().concat(axiosMiddleware)
    }*/
});

export type RootState = ReturnType<typeof store.getState>;
export type RootDispatch = typeof store.dispatch;

export default store;