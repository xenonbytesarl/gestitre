import {SidebarMenuModel} from "@/layout/sidebar/SidebarMenuModel.ts";
import {sidebarMenus} from "@/layout/sidebar/SidebarData.ts";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "@/core/Store.ts";

interface SidebarState {
    sidebarMenus: SidebarMenuModel[];
    selectedSidebarMenu?: SidebarMenuModel;
}

const initialState: SidebarState = {
    sidebarMenus: sidebarMenus
};

const sidebarSlice = createSlice({
    name: "sidebarMenu",
    initialState,
    reducers: {
        selectSidebarMenu: (state, action: PayloadAction<string>) => {
            state.selectedSidebarMenu = sidebarMenus.find(sidebarMenu => sidebarMenu.link === action.payload);
        }
    }
});


export const selectSidebarMenus = (state: RootState) => state.sidebar.sidebarMenus;
export const selectSelectedSidebarMenu = (state: RootState) => state.sidebar.selectedSidebarMenu;

export const {selectSidebarMenu} = sidebarSlice.actions;

export default sidebarSlice.reducer;