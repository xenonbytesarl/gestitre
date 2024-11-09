import {AxiosError} from "axios";
import {ErrorResponseModel} from "@/shared/model/errorResponseModel.ts";
import {UNKNOWN_ERROR} from "@/shared/constant/globalConstant.ts";

// @ts-ignore
export const handleApiError = (apiError: AxiosError<ErrorResponseModel>, {rejectWithValue})=>  {
    {
        const error = apiError as AxiosError<ErrorResponseModel>;
        if (error && error.response && error.response.data) {
            return rejectWithValue(error.response.data);
        } else if (error.message) {
            return rejectWithValue(error.message);
        } else {
            return rejectWithValue(UNKNOWN_ERROR);
        }
    }
}