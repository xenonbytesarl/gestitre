import {createAsyncThunk, createEntityAdapter, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {handleApiError} from "@/shared/utils/apiError.ts";
import {AxiosError} from "axios";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import {DEFAULT_SIZE_VALUE} from "@/shared/constant/page.constant.ts";
import userService from "@/pages/admin/user/UserService.ts";
import {UserModel} from "@/pages/admin/user/UserModel.ts";
import {RootState} from "@/core/Store.ts";

const userEntityAdapter = createEntityAdapter<UserModel>({});

const userInitialState = userEntityAdapter.getInitialState({
    loading: false,
    currentUser: null as any,
    pageSize: DEFAULT_SIZE_VALUE,
    totalElements: 0,
    totalPages: 0,
    message: '',
    error: null
});

export const findUserById = createAsyncThunk('user/findUserById', async (userId: string, {rejectWithValue})=> {
    try {
        const response =  await userService.findUserById(userId);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const searchUsers = createAsyncThunk('user/searchUsers', async (searchParam: SearchParamModel, {rejectWithValue})=> {
    try {
        const response =  await userService.searchUsers(searchParam);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const createUser = createAsyncThunk('user/createUser', async (user: UserModel, {rejectWithValue})=> {
    try {
        const response =  await userService.createUser(user);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

const userSlice = createSlice({
    name: "user",
    initialState: userInitialState,
    reducers: {
        resetCurrentUser: (state) => {
            state.currentUser = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(createUser.fulfilled, (state, action) => {
                const {content, message} = action.payload;
                state.loading = false;
                state.totalElements = state.totalElements + 1;
                state.message = message;
                state.currentUser = content;
                userEntityAdapter.addOne(state, content);
            })
            .addCase(searchUsers.fulfilled, (state, action) => {
                const {content} = action.payload;
                state.loading = false;
                state.pageSize = content.pageSize as number;
                state.totalElements = content.totalElements as number;
                state.totalPages = content.totalPages as number;
                userEntityAdapter.setAll(state, content.elements);
            })
            .addCase(findUserById.fulfilled, (state, action) => {
                const {content} = action.payload;
                state.loading = false;
                state.currentUser = content;
                userEntityAdapter.addOne(state, content);
            })
            .addMatcher(
                isAnyOf(
                    searchUsers.pending
                ), (state) => {
                    state.loading = true;
                    state.message = '';
                    state.currentUser = null;
                    state.error = null;
                }
            )
            .addMatcher(
                isAnyOf(
                    searchUsers.rejected
                ), (state, action) => {
                    state.loading = false;
                    state.message = '';
                    state.currentUser = null;
                    state.error = action.payload as any;
                }
            )
    }
});

export const getPageSize = (state: RootState) => state.admin.user.pageSize;
export const getTotalElements = (state: RootState) => state.admin.user.totalElements;
export const getTotalPages = (state: RootState) => state.admin.user.totalPages;
export const getMessage = (state: RootState) => state.admin.user.message;
export const getCurrentUser = (state: RootState) => state.admin.user.currentUser;
export const getError = (state: RootState) => state.admin.user.error;
export const getLoading = (state: RootState) => state.admin.user.loading;

export const {
    selectAll: selectCompanies,
    selectIds: selectUserIds,
    selectById: selectUserById,
    selectEntities: selectUserEntities,
} = userEntityAdapter.getSelectors((state: RootState) => state.admin.user);

export const {resetCurrentUser} = userSlice.actions;

export default userSlice.reducer;