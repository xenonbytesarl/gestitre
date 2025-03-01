import {createAsyncThunk, createEntityAdapter, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {AxiosError} from "axios";
import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {DEFAULT_SIZE_VALUE} from "@/shared/constant/page.constant.ts";
import companyService from "@/pages/company/CompanyService.ts";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import {RootState} from "@/core/Store.ts";
import {handleApiError} from "@/shared/utils/apiError.ts";
import {FindParamModel} from "@/shared/model/findParamModel.ts";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";


const companyEntityAdapter = createEntityAdapter<CompanyModel>({});

const companyInitialState = companyEntityAdapter.getInitialState({
    loading: false,
    currentCompany: null as any,
    pageSize: DEFAULT_SIZE_VALUE,
    totalElements: 0,
    totalPages: 0,
    message: '',
    error: null
});

export const createCompany = createAsyncThunk('company/createCompany', async ({company, logo, stamp}: {company: CompanyModel, logo: File, stamp: File}, {rejectWithValue})=> {
    try {
        const response =  await companyService.createCompany(company, logo, stamp);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const findCompanyById = createAsyncThunk('company/findCompanyById', async (companyId: string, {rejectWithValue})=> {
    try {
        const response =  await companyService.findCompanyById(companyId);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const findCompanies = createAsyncThunk('company/findCompanies', async (findParam: FindParamModel, {rejectWithValue})=> {
    try {
        const response =  await companyService.findCompanies(findParam);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

export const searchCompanies = createAsyncThunk('company/searchCompanies', async (searchParam: SearchParamModel, {rejectWithValue})=> {
    try {
        const response =  await companyService.searchCompanies(searchParam);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

const companySlice = createSlice({
    name: "company",
    initialState: companyInitialState,
    reducers: {
        resetCurrentCompany: (state) => {
            state.currentCompany = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(createCompany.fulfilled, (state, action) => {
                const {content, message} = action.payload;
                state.loading = false;
                state.totalElements = state.totalElements + 1;
                state.message = message;
                state.currentCompany = content;
                companyEntityAdapter.addOne(state, content);
            })
            .addCase(findCompanyById.fulfilled, (state, action) => {
                const {content} = action.payload;
                state.loading = false;
                state.currentCompany = content;
                companyEntityAdapter.addOne(state, content);
            })
            .addMatcher(
                isAnyOf(
                    findCompanies.fulfilled,
                    searchCompanies.fulfilled
                ), (state, action) => {
                    const {content} = action.payload;
                    state.loading = false;
                    state.pageSize = content.pageSize as number;
                    state.totalElements = content.totalElements as number;
                    state.totalPages = content.totalPages as number;
                    companyEntityAdapter.setAll(state, content.elements);
                })
            .addMatcher(
                isAnyOf(
                    createCompany.pending,
                    findCompanyById.pending,
                    findCompanies.pending,
                    searchCompanies.pending
            ), (state) => {
                state.loading = true;
                state.message = '';
                state.currentCompany = null;
                state.error = null;
            })
            .addMatcher(
                isAnyOf(
                    createCompany.rejected,
                    findCompanyById.rejected,
                    findCompanies.rejected,
                    searchCompanies.rejected
                ), (state, action) => {
                    state.loading = false;
                    state.message = '';
                    state.currentCompany = null;
                    state.error = action.payload as any;
            })
    }
});

export const getPageSize = (state: RootState) => state.company.pageSize;
export const getTotalElements = (state: RootState) => state.company.totalElements;
export const getTotalPages = (state: RootState) => state.company.totalPages;
export const getMessage = (state: RootState) => state.company.message;
export const getCurrentCompany = (state: RootState) => state.company.currentCompany;
export const getError = (state: RootState) => state.company.error;
export const getLoading = (state: RootState) => state.company.loading;

export const {
    selectAll: selectCompanies,
    selectIds: selectCompanyIds,
    selectById: selectCompanyById,
    selectEntities: selectCompanyEntities,
} = companyEntityAdapter.getSelectors((state: RootState) => state.company);

export const {resetCurrentCompany} = companySlice.actions;

export default companySlice.reducer;