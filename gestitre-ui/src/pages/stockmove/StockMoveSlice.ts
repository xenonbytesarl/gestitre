import {createAsyncThunk, createEntityAdapter, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {DEFAULT_SIZE_VALUE} from "@/shared/constant/page.constant.ts";
import {StockMoveModel} from "@/pages/stockmove/StockMoveModel.ts";
import {handleApiError} from "@/shared/utils/apiError.ts";
import {AxiosError} from "axios";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import stockMoveService from "@/pages/stockmove/StockMoveService.ts";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {RootState} from "@/core/Store.ts";


const stockMoveEntityAdapter = createEntityAdapter<StockMoveModel>({});

const stockMoveInitialState = stockMoveEntityAdapter.getInitialState({
    loading: false,
    currentStockMove: null as any,
    pageSize: DEFAULT_SIZE_VALUE,
    totalElements: 0,
    totalPages: 0,
    message: '',
    error: null
});

export const createStockMove = createAsyncThunk('stockMove/createStockMove', async ({stockMove, file}: {stockMove: StockMoveModel, file: File}, {rejectWithValue})=> {
    try {
        const response =  await stockMoveService.createStockMove(stockMove, file);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const searchStockMoves = createAsyncThunk('stockMove/searchStockMoves', async (searchParam: SearchParamModel, {rejectWithValue})=> {
    try {
        const response =  await stockMoveService.searchStockMoves(searchParam);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

const stockMoveSlice = createSlice({
    name: "stockMove",
    initialState: stockMoveInitialState,
    reducers: {
        resetCurrentStockMove: (state) => {
            state.currentStockMove = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(createStockMove.fulfilled, (state, action) => {
                const {content, message} = action.payload;
                state.loading = false;
                state.totalElements = state.totalElements + 1;
                state.message = message;
                state.currentStockMove = content;
                stockMoveEntityAdapter.addOne(state, content);
            })
            .addMatcher(
                isAnyOf(
                    createStockMove.pending
                ), (state) => {
                    state.loading = true;
                    state.message = '';
                    state.currentStockMove = null;
                    state.error = null;
                })
            .addMatcher(
                isAnyOf(
                    createStockMove.rejected
                ), (state, action) => {
                    state.loading = false;
                    state.message = '';
                    state.currentStockMove = null;
                    state.error = action.payload as any;
                })
    }
});


export const getPageSize = (state: RootState) => state.stockMove.pageSize;
export const getTotalElements = (state: RootState) => state.stockMove.totalElements;
export const getTotalPages = (state: RootState) => state.stockMove.totalPages;
export const getMessage = (state: RootState) => state.stockMove.message;
export const getCurrentStockMove = (state: RootState) => state.stockMove.currentStockMove;
export const getError = (state: RootState) => state.stockMove.error;
export const getLoading = (state: RootState) => state.stockMove.loading;

export const {
    selectAll: selectStockMoves,
    selectIds: selectStockMoveIds,
    selectById: selectStockMoveById,
    selectEntities: selectStockMoveEntities,
} = stockMoveEntityAdapter.getSelectors((state: RootState) => state.stockMove);

export const {resetCurrentStockMove} = stockMoveSlice.actions;

export default stockMoveSlice.reducer;