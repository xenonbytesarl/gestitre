import {CertificateTemplateModel} from "@/pages/company/certificatetemplate/CertificateTemplateModel.ts";
import {createAsyncThunk, createEntityAdapter, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {DEFAULT_SIZE_VALUE} from "@/shared/constant/page.constant.ts";
import {FindParamModel} from "@/shared/model/findParamModel.ts";
import certificateTemplateService from "@/pages/company/certificatetemplate/CertificateService.ts";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import {AxiosError} from "axios";
import {handleApiError} from "@/shared/utils/apiError.ts";
import {RootState} from "@/Store.ts";


const certificateTemplateEntityAdapter = createEntityAdapter<CertificateTemplateModel>({
});

const certificateTemplateInitialState = certificateTemplateEntityAdapter.getInitialState({
    loading: false,
    currentCertificateTemplate: null as any,
    pageSize: DEFAULT_SIZE_VALUE,
    totalElements: 0,
    totalPages: 0,
    message: '',
    error: null
});


export const findCertificateTemplates = createAsyncThunk('certificateTemplate/findCertificateTemplates', async (findParam: FindParamModel, {rejectWithValue})=> {
    try {
        const response =  await certificateTemplateService.findCertificateTemplates(findParam);
        // @ts-ignore
        return {content: response.data.data.content, message: response.data.message}
    } catch (apiError) {
        return handleApiError(apiError as AxiosError<ErrorResponseModel>, {rejectWithValue});
    }
});

const certificateTemplateSlice = createSlice({
    name: "certificateTemplate",
    initialState: certificateTemplateInitialState,
    reducers: {
        resetCurrentCertificateTemplate: (state) => {
            state.currentCertificateTemplate = null;
        }
    },
    extraReducers: (builder) => {
        builder
            .addMatcher(
                isAnyOf(
                    findCertificateTemplates.fulfilled
                ), (state, action) => {
                    const {content} = action.payload;
                    state.loading = false;
                    state.pageSize = content.pageSize as number;
                    state.totalElements = content.totalElements as number;
                    state.totalPages = content.totalPages as number;
                    certificateTemplateEntityAdapter.setAll(state, content.elements);
                })
            .addMatcher(
                isAnyOf(
                    findCertificateTemplates.pending
                ), (state) => {
                    state.loading = true;
                    state.message = '';
                    state.currentCertificateTemplate = null;
                    state.error = null;
                })
            .addMatcher(
                isAnyOf(
                    findCertificateTemplates.rejected
                ), (state, action) => {
                    state.loading = false;
                    state.message = '';
                    state.currentCertificateTemplate = null;
                    state.error = action.payload as any;
                })
    }
});

export const getPageSize = (state: RootState) => state.certificateTemplate.pageSize;
export const getTotalElements = (state: RootState) => state.certificateTemplate.totalElements;
export const getTotalPages = (state: RootState) => state.certificateTemplate.totalPages;
export const getMessage = (state: RootState) => state.certificateTemplate.message;
export const getCurrentCertificateTemplate = (state: RootState) => state.certificateTemplate.currentCertificateTemplate;
export const getError = (state: RootState) => state.certificateTemplate.error;
export const getLoading = (state: RootState) => state.certificateTemplate.loading;

export const {
    selectAll: selectCertificateTemplates,
    selectIds: selectCertificateTemplateIds,
    selectById: selectCertificateTemplateById,
    selectEntities: selectCertificateTemplateEntities,
} = certificateTemplateEntityAdapter.getSelectors((state: RootState) => state.certificateTemplate);

export const {resetCurrentCertificateTemplate} = certificateTemplateSlice.actions;

export default certificateTemplateSlice.reducer;
