
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "@/Store.ts";
import {LanguageModel} from "@/layout/navbar/LanguageModel.ts";


interface NavbarState {
    language: LanguageModel;
}

const initialState: NavbarState = {
    language: {
        name: 'fr'
    }
}

const navbarSlice = createSlice({
    name: "navbar",
    initialState,
    reducers: {
        changeLanguage: (state, action: PayloadAction<LanguageModel>) => {
            state.language = action.payload;
        }
    }
});

export const selectLanguage = (state: RootState) => state.navbar.language;

export const {changeLanguage} = navbarSlice.actions;
export default navbarSlice.reducer;