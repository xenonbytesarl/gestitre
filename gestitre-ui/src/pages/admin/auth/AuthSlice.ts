import {createAsyncThunk, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {LoginRequestModel} from "@/pages/admin/auth/LoginRequestModel.ts";
import {handleApiError} from "@/shared/utils/apiError.ts";
import {AxiosError} from "axios";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import authService from "@/pages/admin/auth/AuthService.ts";
import {RootState} from "@/core/Store.ts";
import {VerifyCodeRequestModel} from "@/pages/admin/auth/VerifyCodeRequestModel.ts";
import {LoginResponseModel} from "@/pages/admin/auth/LoginResponseModel.ts";
import {
    cleanAuthenticationInformation,
    cleanLastVisitedUrl,
    cleanMfaInformation,
    cleanProfileInformation,
    persistAuthenticationInformation,
    persistMfaInformation,
    persistProfileInformation,
    recoverAuthenticationInformation,
    recoverMfaInformation,
    recoverProfileInformation
} from "@/shared/utils/localStorageUtils.ts";
import {ProfileModel} from "@/pages/admin/user/ProfileModel.ts";
import {LanguageEnum} from "@/pages/admin/user/LanguageEnum.ts";

interface VerifyCodeInfo {
    email: string;
    tenantCode: string;
}

interface AuthState {
    loading: boolean;
    loginResponse: LoginResponseModel;
    verifyCodeInfo: VerifyCodeInfo;
    isAuthenticated: boolean;
    profile: ProfileModel,
    message: string;
    error: any;
}

const defaultLoginResponse: LoginResponseModel = {
    accessToken: '',
    refreshToken: '',
    isMfa: false
};

const defaultVerifyCodeInfo: VerifyCodeInfo = {
    email: '',
    tenantCode: ''
}

const defaultProfile: ProfileModel = {
    companyId: '',
    tenantId: '',
    name: '',
    language: LanguageEnum.FR
}

const authInitialState: AuthState = {
    loading: false,
    loginResponse: defaultLoginResponse,
    verifyCodeInfo: defaultVerifyCodeInfo,
    profile: defaultProfile,
    isAuthenticated: false,
    message: '',
    error: null
}

export const login = createAsyncThunk('auth/login', async({loginRequest}: {loginRequest: LoginRequestModel}, {rejectWithValue} ) => {
    try {
        const response =  await authService.login(loginRequest);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message, email: loginRequest.email, tenantCode: loginRequest.tenantCode}
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

export const refreshToken = createAsyncThunk('auth/refreshToken', async(_, {rejectWithValue} ) => {
    try {
        const response =  await authService.refreshToken();
        // @ts-ignore
        //persistAuthenticationInformation(response.data.data.content.accessToken, response.data.data.content.refreshToken);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const persistAuthentication = createAsyncThunk("auth/persistAuthentication", async({accessToken, refreshToken}: {accessToken: string, refreshToken: string}) => {
    persistAuthenticationInformation(accessToken, refreshToken);
});

export const persistMfa = createAsyncThunk("auth/persistMfa", async({email, tenantCode}: {email: string, tenantCode: string}) => {
    persistMfaInformation(email, tenantCode);
});

export const logout = createAsyncThunk("auth/logout", async() => {
    cleanAuthenticationInformation();
    cleanLastVisitedUrl();
    cleanProfileInformation();
});

export const cleanMfa = createAsyncThunk("auth/cleanMfa", async() => {
    cleanMfaInformation();
});

export const recoverAuthentication = createAsyncThunk("auth/recoverAuthentication", () => {
    const response = recoverAuthenticationInformation();
    const isAuthenticated = response.accessToken !== '' && response.refreshToken !== '';
    return {content: response, isAuthenticated};
});

export const recoverProfile = createAsyncThunk("auth/recoverProfile", () => {
    const response = recoverProfileInformation();
    return {content: response};
});

export const recoverMfa = createAsyncThunk("auth/recoverMfa", () => {
    const response = recoverMfaInformation();
    return {content: response};
});

export const getProfile = createAsyncThunk('auth/getProfile', async (_, {rejectWithValue})=> {
    try {
        const response =  await authService.getProfile();
        // @ts-ignore
        persistProfileInformation(response.data.data.content)
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
                const {content, message, email, tenantCode} = action.payload;
                state.loading = false;
                state.message = message;
                state.isAuthenticated = !content.isMfa;
                state.verifyCodeInfo = {email: content.isMfa ? email : '', tenantCode: content.isMfa ? tenantCode: ''};
                state.loginResponse = content;
            })
            .addCase(verifyCode.fulfilled, (state, action)=> {
                const {content, message} = action.payload;
                state.loading = false;
                state.message = message;
                state.isAuthenticated = true;
                state.verifyCodeInfo = defaultVerifyCodeInfo;
                state.loginResponse = content;
            })
            .addCase(refreshToken.fulfilled, (state, action)=> {
                const {content, message} = action.payload;
                state.loading = false;
                state.message = message;
                state.isAuthenticated = true;
                state.loginResponse = {...content};
            })
            .addCase(getProfile.fulfilled, (state, action) => {
                const {content} = action.payload;
                state.loading = false;
                state.profile = content;
            })
            .addCase(refreshToken.pending, (state)=> {
                state.loading = true;
                state.message = '';
                state.isAuthenticated = true;
                state.error = null;
            })
            .addCase(recoverAuthentication.fulfilled, (state, action)=> {
                const {content, isAuthenticated} = action.payload;
                state.loading = false;
                state.isAuthenticated = isAuthenticated;
                state.loginResponse = content;
            })
            .addCase(recoverProfile.fulfilled, (state, action)=> {
                const {content} = action.payload;
                state.loading = false;
                state.profile = content;
            })
            .addCase(recoverMfa.fulfilled, (state, action)=> {
                const {content} = action.payload;
                state.loading = false;
                state.isAuthenticated = false;
                state.verifyCodeInfo = {...content};
            })
            .addCase(logout.fulfilled, (state) => {
                state.isAuthenticated = false;
                state.loginResponse = defaultLoginResponse;
                state.verifyCodeInfo = defaultVerifyCodeInfo;
                state.loading = false;
                state.message = '';
                state.error = null;
            })
            .addMatcher(isAnyOf(
                recoverAuthentication.pending,
                recoverProfile.pending) , (state)=> {
                state.loading = true;
            })
            .addMatcher(isAnyOf(
                persistAuthentication.fulfilled,
                persistMfa.fulfilled,
                cleanMfa.fulfilled
            ), (state) => {
                state.loading = false;
            })
            .addMatcher(isAnyOf(
                login.rejected,
                verifyCode.rejected,
                refreshToken.rejected,
                recoverAuthentication.rejected,
                recoverMfa.rejected,
                recoverProfile.rejected,
                getProfile.rejected
            ),(state, action) => {
                state.loading = false;
                state.loginResponse = defaultLoginResponse;
                state.verifyCodeInfo = defaultVerifyCodeInfo;
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
export const getVerifyCodeInfo = (state: RootState) => state.admin.auth.verifyCodeInfo;
export const getProfileInfo = (state: RootState) => state.admin.auth.profile;
export const getMessage = (state: RootState) => state.admin.auth.message;
export const getError = (state: RootState) => state.admin.auth.error;

export default authSlice.reducer;