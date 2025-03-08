import {createAsyncThunk, createEntityAdapter, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {DEFAULT_SIZE_VALUE} from "@/shared/constant/page.constant.ts";
import {handleApiError} from "@/shared/utils/apiError.ts";
import {AxiosError} from "axios";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {ShareHolderModel} from "@/pages/shareholder/ShareHolderModel.ts";
import shareHolderService from "@/pages/shareholder/ShareHolderService.ts";
import {RootState} from "@/core/Store.ts";

const shareHolderEntityAdapter = createEntityAdapter<ShareHolderModel>({});

const shareHolderInitialState = shareHolderEntityAdapter.getInitialState({
    loading: false,
    currentShareHolder: null as any,
    pageSize: DEFAULT_SIZE_VALUE,
    totalElements: 0,
    roles: [],
    totalPages: 0,
    message: '',
    error: null
});

export const findShareHolderById = createAsyncThunk('shareHolder/findShareHolderById', async (shareHolderId: string, {rejectWithValue})=> {
    try {
        const response =  await shareHolderService.findShareHolderById(shareHolderId);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const searchShareHolders = createAsyncThunk('shareHolder/searchShareHolders', async (searchParam: SearchParamModel, {rejectWithValue})=> {
    try {
        const response =  await shareHolderService.searchShareHolders(searchParam);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const createShareHolder = createAsyncThunk('shareHolder/createShareHolder', async (shareHolder: ShareHolderModel, {rejectWithValue})=> {
    try {
        const response =  await shareHolderService.createShareHolder(shareHolder);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const updateShareHolder = createAsyncThunk('shareHolder/updateShareHolder', async (shareHolder: ShareHolderModel, {rejectWithValue})=> {
    try {
        const response =  await shareHolderService.updateShareHolder(shareHolder);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

const shareHolderSlice = createSlice({
    name: "shareHolder",
    initialState: shareHolderInitialState,
    reducers: {
        resetCurrentShareHolder: (state) => {
            state.currentShareHolder = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(createShareHolder.fulfilled, (state, action) => {
                const {content, message} = action.payload;
                state.loading = false;
                state.totalElements = state.totalElements + 1;
                state.message = message;
                state.currentShareHolder = content;
                shareHolderEntityAdapter.addOne(state, content);
            })
            .addCase(updateShareHolder.fulfilled, (state, action) => {
                const {content, message} = action.payload;
                state.loading = false;
                state.message = message;
                state.currentShareHolder= content;
                shareHolderEntityAdapter.updateOne(state, content);
            })
            .addCase(searchShareHolders.fulfilled, (state, action) => {
                const {content} = action.payload;
                state.loading = false;
                state.pageSize = content.pageSize as number;
                state.totalElements = content.totalElements as number;
                state.totalPages = content.totalPages as number;
                shareHolderEntityAdapter.setAll(state, content.elements);
            })
            .addCase(findShareHolderById.fulfilled, (state, action) => {
                const {content} = action.payload;
                state.loading = false;
                state.currentShareHolder = content;
                shareHolderEntityAdapter.addOne(state, content);
            })
            .addMatcher(
                isAnyOf(
                    createShareHolder.pending,
                    findShareHolderById.pending,
                    searchShareHolders.pending,
                    updateShareHolder.pending
                ), (state) => {
                    state.loading = true;
                    state.message = '';
                    state.currentShareHolder = null;
                    state.error = null;
                }
            )
            .addMatcher(
                isAnyOf(
                    createShareHolder.rejected,
                    findShareHolderById.rejected,
                    searchShareHolders.rejected,
                    updateShareHolder.rejected
                ), (state, action) => {
                    state.loading = false;
                    state.message = '';
                    state.currentShareHolder = null;
                    state.error = action.payload as any;
                }
            )
    }
});

export const getPageSize = (state: RootState) => state.shareHolder.pageSize;
export const getTotalElements = (state: RootState) => state.shareHolder.totalElements;
export const getTotalPages = (state: RootState) => state.shareHolder.totalPages;
export const getMessage = (state: RootState) => state.shareHolder.message;
export const getCurrentShareHolder = (state: RootState) => state.shareHolder.currentShareHolder;
export const getError = (state: RootState) => state.shareHolder.error;
export const getLoading = (state: RootState) => state.shareHolder.loading;

export const {
    selectAll: selectShareHolders,
    selectIds: selectShareHolderIds,
    selectById: selectShareHolderById,
    selectEntities: selectShareHolderEntities,
} = shareHolderEntityAdapter.getSelectors((state: RootState) => state.shareHolder);

export const {resetCurrentShareHolder} = shareHolderSlice.actions;

export default shareHolderSlice.reducer;

