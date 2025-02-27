import {createAsyncThunk, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {LoginRequestModel} from "@/pages/admin/auth/LoginRequestModel.ts";
import {handleApiError} from "@/shared/utils/apiError.ts";
import {AxiosError} from "axios";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import authService from "@/pages/admin/auth/AuthService.ts";
import {RootState} from "@/Store.ts";
import {VerifyCodeRequestModel} from "@/pages/admin/auth/VerifyCodeRequestModel.ts";

const authInitialState = {
    loading: false,
    loginResponse: {},
    isAuthenticated: false,
    message: '',
    error: null
}

export const login = createAsyncThunk('auth/login', async({loginRequest}: {loginRequest: LoginRequestModel}, {rejectWithValue} ) => {
    try {
        const response =  await authService.login(loginRequest);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const verifyCode = createAsyncThunk('auth/verifyCode', async({verifyCodeRequest}: {verifyCodeRequest: VerifyCodeRequestModel}, {rejectWithValue} ) => {
    try {
        const response =  await authService.verifyCode(verifyCodeRequest);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

const authSlice = createSlice({
    name: "auth",
    initialState: authInitialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(login.fulfilled, (state, action)=> {
                const {content, message} = action.payload;
                state.loading = false;
                state.message = message;
                state.isAuthenticated = !content.isMfa;
                state.loginResponse = content;
            })
            .addCase(verifyCode.fulfilled, (state, action)=> {
                const {content, message} = action.payload;
                state.loading = false;
                state.message = message;
                state.isAuthenticated = true;
                state.loginResponse = content;
            })
            .addMatcher(isAnyOf(
                login.rejected,
                verifyCode.rejected
            ),(state, action) => {
                state.loading = false;
                state.message = '';
                state.isAuthenticated = false;
                state.error = action.payload as any;
            })
            .addMatcher(
                isAnyOf(
                    login.pending,
                    verifyCode.pending
                ), (state) => {
                    state.loading = true;
                    state.message = '';
                    state.isAuthenticated = false;
                    state.error = null;
            })

    }
});

export const getIsAuthenticated = (state: RootState) => state.admin.auth.isAuthenticated;
export const getLoading = (state: RootState) => state.admin.auth.loading;
export const getLoginResponse = (state: RootState) => state.admin.auth.loginResponse;
export const getMessage = (state: RootState) => state.admin.auth.message;
export const getError = (state: RootState) => state.admin.auth.error;

export default authSlice.reducer;